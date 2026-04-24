package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
    Author: Ryan Page, Michael Healy
    Date: 04/23/2026
    Description: Aggregate Reducer for MapReduce, Aggregates Frequency Counts for each N-Gram
    Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
*/

// Aggregate Frequencies for each N-Gram instance
public class Reduce_Frequencies extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable frequency = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Initializes Aggregate Frequency Sum
        int freq_sum = 0;

        for (IntWritable val : values) {
            // Combines Frequency Values
            freq_sum += val.get();
        }

        // Passes Frequency of each N-Gram through Context to write to IntermediateFile, for use as input in InverseMap
        frequency.set(freq_sum);
        context.write(key, frequency);
    }

}