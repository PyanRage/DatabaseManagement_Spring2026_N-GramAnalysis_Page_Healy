package nGramAnalysis;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

/*
    Author: Ryan Page, Michael Healy
    Date: 04/23/2026
    Description: MAIN DRIVER FILE for MapReduce
    Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
*/

public class MainDriver {
    public static void main(String[] args) throws Exception {

        // Exits Program if Incorrect amount of arguments for proper Program Execution
        if (args.length != 3) {
            System.out.println("Usage: MainDriver <input dir> <temp dir> <output dir>");
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

        // Takes Input Text File as Input, Produces Temporary File for Use in Job2
        FileInputFormat.addInputPath(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));

        job1.waitForCompletion(true);

        // Start Job 2: Inverse Map, Return Top-K Ranked N-Grams
        Configuration conf2 = new Configuration();
        Job job2 = Job.getInstance(conf2, "Top-K Ranking");

        job2.setJarByClass(MainDriver.class);

        job2.setMapperClass(InverseMap_Freq.class);
        job2.setReducerClass(Reduce_TopK.class);

        job2.setMapOutputKeyClass(IntWritable.class);
        job2.setMapOutputValueClass(Text.class);

        job2.setSortComparatorClass(DescendingComparator.class);
        job2.setNumReduceTasks(1);

        job2.setOutputKeyClass(IntWritable.class);
        job2.setOutputValueClass(Text.class);

        // Takes Temporary File created by Job1 as Input, Produces Output File containing Top-K Ranked N-Grams
        FileInputFormat.addInputPath(job2, new Path(args[1]));
        FileOutputFormat.setOutputPath(job2, new Path(args[2]));

        boolean success = job2.waitForCompletion(true);
        System.exit(success ? 0 : 1);
    }
}