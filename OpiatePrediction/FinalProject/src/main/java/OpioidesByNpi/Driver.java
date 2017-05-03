/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OpioidesByNpi;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 *
 * @author vishakha
 */
public class Driver {
    
       public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "PrescribedOpioidsByNPI");
        job.setJarByClass(Driver.class);
        job.setMapperClass(DriverMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
//        job.setCombinerClass(InvertedReducer.class);
        job.setReducerClass(DriverReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
       
     public static class DriverMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text drugname = new Text();
        private Text npi = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            if(key.get()==0){
            return;
        }
            String tokens[] = value.toString().split("\t");
            drugname.set(tokens[1]);
            npi.set(tokens[0]);
            context.write(npi,drugname);
        }

    }

    public static class DriverReducer extends Reducer<Text, Text, Text, Text> {

        private Text result = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();

            boolean first = true;

            for (Text id : values) {
                if (first) {
                    first = false;
                } else {
                    sb.append("\t");
                }
                sb.append(id.toString());

            }
            result.set(sb.toString());
            context.write(key, result);
        }

    }   
    
}
