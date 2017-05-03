/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrescribedOpioidStats;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
        Job job = Job.getInstance(conf, "PrescribedOpioids");
        job.setJarByClass(Driver.class);

        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, DriverOpioidsCountMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, DriverOpioidsMapper.class);

        job.setReducerClass(DriverReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class DriverOpioidsCountMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text drugname = new Text();
        private Text outvalue = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            if (key.get() == 0 && value.toString().contains("NPPES") /*Some condition satisfying it is header*/) {
                return;
            }
            String values[] = value.toString().split("\t");
            drugname.set(values[1]);
            outvalue.set("C" + value.toString());
            context.write(drugname, outvalue);
        }

    }

    public static class DriverOpioidsMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text drugname = new Text();
        private Text outvalue = new Text();

        @Override
        protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {

            if (key.get() == 0 && value.toString().contains("Generic Name")/*Some condition satisfying it is header*/) {
                return;
            }
            String values[] = value.toString().split(",");
            System.out.println(values[0]);
            drugname.set(values[0]);
            outvalue.set("D" + value.toString());
            context.write(drugname, outvalue);
        }

    }

    public static class DriverReducer extends Reducer<Text, Text, Text, NullWritable> {

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

        private void executeInnerJoinLogic(Context context) throws IOException, InterruptedException {
            if (!listC.isEmpty() && !listD.isEmpty()) {
                for (Text C : listC) {
//                    for (Text D : listD) 
                    {
                        context.write(C, NullWritable.get());
                    }
                }
            }
        }

    }
}
