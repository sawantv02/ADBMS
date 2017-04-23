/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prescriberdata;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
    
    public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Prescriber");
        job.setJarByClass(Driver.class);
        job.setMapperClass(DriverMapper.class);
//        job.setCombinerClass(DriverReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(PrescriberDetailsWritable.class);
        job.setReducerClass(DriverReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(PrescriberDetailsWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
    
    public static class DriverMapper extends Mapper<LongWritable, Text, Text,PrescriberDetailsWritable> {
        private Text npi = new Text();
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            
            if (key.get() == 0 && value.toString().contains("NPPES") /*Some condition satisfying it is header*/)
                return;
            String values[] = value.toString().split("\t");
            npi.set(values[0]);
            PrescriberDetailsWritable pdw=new PrescriberDetailsWritable(values[1],values[2],values[12],values[14],values[4],values[5]);
//            System.err.println(pdw);
            context.write(npi,pdw);
        }
        
    }
    
    public static class DriverReducer extends Reducer<Text,PrescriberDetailsWritable,Text,PrescriberDetailsWritable>{

        PrescriberDetailsWritable pdw;
        @Override
        protected void reduce(Text key, Iterable<PrescriberDetailsWritable> values, Context context) throws IOException, InterruptedException {
            for(PrescriberDetailsWritable val:values){
                pdw=new PrescriberDetailsWritable(val.getLast_name(),val.getFirst_name(),val.getState(),val.getSpeciality(),val.getCreds(),val.getGender());
            }
            context.write(key,pdw);
        }
        
    }
    
}
