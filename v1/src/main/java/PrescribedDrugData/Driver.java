/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrescribedDrugData;

import Prescriberdata.PrescriberDetailsWritable;
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
        public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "PrescribedDrugs");
        job.setJarByClass(Driver.class);
        job.setMapperClass(DriverMapper.class);
//        job.setCombinerClass(DriverReducer.class);
        job.setMapOutputKeyClass(PrescribedDrugWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(DriverReducer.class);
        job.setOutputKeyClass(PrescribedDrugWritable.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
        public static class DriverMapper extends Mapper<LongWritable, Text,PrescribedDrugWritable,IntWritable> {
        private IntWritable totalDrugSupply=new IntWritable(0);    
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            
            if (key.get() == 0 && value.toString().contains("NPPES") /*Some condition satisfying it is header*/)
                return;
            String values[] = value.toString().split("\t");
//            System.out.println(pk);
            PrescribedDrugWritable pdw=new PrescribedDrugWritable(values[0],values[7],values[4],values[8],values[12]);
//            System.out.println(pdw);
            if(values[11].isEmpty()){
                totalDrugSupply.set(0);
            }
            else
                totalDrugSupply.set(Integer.parseInt(values[11]));
            context.write(pdw,totalDrugSupply);
        }
        
    }
    
    public static class DriverReducer extends Reducer<PrescribedDrugWritable,IntWritable,PrescribedDrugWritable,IntWritable>{

        PrescribedDrugWritable pdw;
        IntWritable drugcount=new IntWritable();
        private Text entry=new Text();
        @Override
        protected void reduce(PrescribedDrugWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count=0;
            for(IntWritable val:values){
                count+=Integer.parseInt(val.toString());           
            }
            drugcount.set(count);
            context.write(key,drugcount);
        }
        
    }
    
}
