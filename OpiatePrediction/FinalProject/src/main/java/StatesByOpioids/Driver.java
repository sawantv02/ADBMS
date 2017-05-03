/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatesByOpioids;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 *
 * @author vishakha
 */
public class Driver {

    public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Statewise Analysis by Joining");
        job.setJarByClass(Driver.class);

        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, DriverCountMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, DriverDeathMapper.class);

        job.setReducerClass(DriverJoinReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        boolean complete = job.waitForCompletion(true);

        Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "chaining");

        if (complete) {
            job2.setJarByClass(Driver.class);
            job2.setMapperClass(DriverStateMapper.class);
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(SupplyAndCost.class);

            job2.setReducerClass(DriverStateReducer.class);
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(SupplyAndCost.class);

            FileInputFormat.addInputPath(job2, new Path(args[2]));
            FileOutputFormat.setOutputPath(job2, new Path(args[3]));
            System.exit(job2.waitForCompletion(true) ? 0 : 1);
        }
    }

    public static class DriverCountMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text statekey = new Text();
        private Text count = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            if (key.get() == 0) {
                return;
            }
            String values[] = value.toString().split("\t");
            statekey.set(values[2]);
            count.set('C' + values[2]+"\t"+values[4]);
            context.write(statekey, count);
        }

    }

    public static class DriverDeathMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text statekey = new Text();
        private Text death = new Text();

        @Override
        protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {

            if (key.get() == 0 && value.toString().contains("2014")) {
                return;
            }
            String values[] = value.toString().split(",");
            statekey.set(values[0]);
            death.set('D' + values[0]+"\t"+values[2]);
            context.write(statekey, death);
        }

    }

    public static class DriverJoinReducer extends Reducer<Text, Text, Text, Text> {

        private static final Text Empty_Text = new Text(" ");
        private Text temp = new Text();

        private ArrayList<Text> listC = new ArrayList<>();
        private ArrayList<Text> listD = new ArrayList<>();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            listC.clear();
            listD.clear();

            while (values.iterator().hasNext()) {
                temp = values.iterator().next();

                if (temp.charAt(0) == 'C') {
                    listC.add(new Text(temp.toString().substring(1)));
                } else if (temp.charAt(0) == 'D') {
                    listD.add(new Text(temp.toString().substring(1)));
                }
            }

            executeInnerJoinLogic(context);
        }

        private void executeInnerJoinLogic(Reducer.Context context) throws IOException, InterruptedException {
            if (!listC.isEmpty() && !listD.isEmpty()) {
                for (Text C : listC) {
                    for (Text D : listD) {
                        context.write(C, D);
                    }
                }
            }
        }

    }

    public static class DriverStateMapper extends Mapper<Object, Text, Text, SupplyAndCost> {

        private Text statekey = new Text();

        @Override
        protected void map(Object key, Text value, Mapper.Context context) throws IOException, InterruptedException {

            if (key.toString().isEmpty()) {
                return;
            }
            String values[] = value.toString().split("\t");
            statekey.set(values[0]);
            SupplyAndCost sc = new SupplyAndCost(values[1], values[3]);
            context.write(statekey, sc);
        }

    }

    public static class DriverStateReducer extends Reducer<Text, SupplyAndCost, Text, SupplyAndCost> {

        DoubleWritable drugcount = new DoubleWritable();
        DoubleWritable drugdeath = new DoubleWritable();

        @Override
        protected void reduce(Text key, Iterable<SupplyAndCost> values, Context context) throws IOException, InterruptedException {
            Double count = 0.0;
            Double cost = 0.0;
            for (SupplyAndCost val : values) {
                count += Double.parseDouble(val.getTotalDrugSupply());
                cost= Double.parseDouble(val.getTotalDeath());
            }
            drugcount.set(count);
            drugdeath.set(cost);
            context.write(key, new SupplyAndCost(drugcount.toString(), drugdeath.toString()));
        }

    }
}
