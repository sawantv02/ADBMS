/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpecializedByState;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 * @author vishakha
 */
public class Driver {

    public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Specialities by State");
        job.setJarByClass(Driver.class);
        job.setMapperClass(DriverMapper.class);
//        job.setCombinerClass(DriverReducer.class);
        job.setMapOutputKeyClass(SpecialityDetailsWritable.class);
        job.setGroupingComparatorClass(GroupingStateComparator.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(DriverReducer.class);
        job.setOutputKeyClass(SpecialityDetailsWritable.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static class DriverMapper extends Mapper<LongWritable, Text, SpecialityDetailsWritable, IntWritable> {

        private Text npi = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            if (key.get() == 0 && value.toString().contains("NPPES") /*Some condition satisfying it is header*/) {
                return;
            }
            String values[] = value.toString().split("\t");
            SpecialityDetailsWritable sdw = new SpecialityDetailsWritable(values[0], values[3], values[4]);
            context.write(sdw, new IntWritable(1));
        }
    }

    public static class DriverReducer extends Reducer<SpecialityDetailsWritable, IntWritable, SpecialityDetailsWritable, IntWritable> {

        @Override
        protected void reduce(SpecialityDetailsWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int specialists = 0;
            for (IntWritable val : values) {
                specialists += Integer.parseInt(val.toString());
            }

            context.write(key, new IntWritable(specialists));
        }

    }
}
