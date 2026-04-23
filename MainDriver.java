package org.example;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class MainDriver {
    public static void main(String[] args) throws Exception {

        if (args.length != 3) {
            System.out.printlf("Usage: MainDriver <input dir> <temp dir> <output dir>");
            System.exit(-1);
        }

        // Start Job 1: Calculate the Frequencies of each N-Gram
        Configuration conf = new Configuration();
        Job job1 = Job.getInstance(conf, "N-Gram Frequency");

        job1.setJarByClass(MainDriver.class);

        job1.setMapperClass(Map_NGrams.class);
        job1.setCombinerClass(Combiner_Frequencies.class);
        job1.setReducerClass(Reduce_Frequencies.class);

        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));

        job1.waitForCompletion(true);

        // Start Job 2
        Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "Top-K Ranking");

        job2.setJarByClass(MainDriver.class);

        job2.setMapperClass(InverseMap_Freq.class);
        job2.ReducerClass(Reduce_TopK.class);

        job2.setOutputKeyClass(IntWritable.class);
        job2.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job2, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));

        boolean success = job2.waitForCompletion(true);
        System.exit(success ? 0 : 1);
    }
}