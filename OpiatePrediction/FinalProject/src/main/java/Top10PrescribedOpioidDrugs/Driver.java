/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Top10PrescribedOpioidDrugs;

import PrescribedDrugData.DrugsWithCost;
import PrescribedDrugData.PrescribedDrugWritable;
import java.io.IOException;
import java.util.TreeMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 *
 * @author vishakha
 */
public class Driver {

    public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException, Exception {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Top10PrescribedOpioideDrugsByChaining");
        job.setJarByClass(Driver.class);
        job.setMapperClass(DriverMapper.class);
//        job.setCombinerClass(DriverReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(SupplyAndCost.class);
        job.setReducerClass(DriverReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        boolean complete = job.waitForCompletion(true);

        Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "chaining");
        
        if (complete){
            job2.setJarByClass(Driver.class);
            job2.setMapperClass(DriverMapper2.class);
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(NullWritable.class);

            job2.setNumReduceTasks(0);

            FileInputFormat.addInputPath(job2,new Path(args[1]));
            FileOutputFormat.setOutputPath(job2,new Path(args[2]));
            System.exit(job2.waitForCompletion(true)?0:1);
        }
    }


    public static class DriverMapper extends Mapper<LongWritable, Text, Text, SupplyAndCost> {

        private Text outkey = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            if (key.get() == 0 && value.toString().contains("NPPES") /*Some condition satisfying it is header*/) {
                return;
            }
            String values[] = value.toString().split("\t");
            outkey.set(values[1]);
            SupplyAndCost sc = new SupplyAndCost(values[4], values[5]);
            context.write(outkey, sc);
        }

    }

    public static class DriverReducer extends Reducer<Text, SupplyAndCost, Text, SupplyAndCost> {

        private TreeMap<Text, SupplyAndCost> recordMap = new TreeMap<Text, SupplyAndCost>();
        DoubleWritable drugcount = new DoubleWritable();
        DoubleWritable drugcost = new DoubleWritable();

        @Override
        protected void reduce(Text key, Iterable<SupplyAndCost> values, Context context) throws IOException, InterruptedException {
            Double count = 0.0;
            Double cost = 0.0;
            for (SupplyAndCost val : values) {
                count += Double.parseDouble(val.getTotalDrugSupply());
                cost += Double.parseDouble(val.getTotalDrugCost());
            }
            drugcount.set(count);
            drugcost.set(cost);
            context.write(key, new SupplyAndCost(drugcount.toString(), drugcost.toString()));

        }
    }

    public static class DriverMapper2 extends Mapper<Object, Text, Text, NullWritable> {

        private TreeMap<Double, Text> recordMap = new TreeMap<>();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            Double supply = Double.parseDouble(value.toString().split("\t")[1]);
            Double cost = Double.parseDouble(value.toString().split("\t")[2]);

            recordMap.put(supply, new Text(value.toString()));

            if (recordMap.size() > 10) {
                recordMap.remove(recordMap.firstKey());
            }

        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            for (Double c : recordMap.descendingKeySet()) {
                context.write(recordMap.get(c), NullWritable.get());
            }
        }

    }

}
