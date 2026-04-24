package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
    Author: Ryan Page, Michael Healy
    Date: 04/23/2026
    Description: Combiner for MapReduce, Reduces Network and Memory Usage
    Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
*/

// "Mini-Reducer"
public class Combiner_Frequencies extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable frequency = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Initializes Frequency Sum
        int freq_sum = 0;

        for (IntWritable val : values) {
            // Combines Frequency Vales
            freq_sum += val.get();
        }

        // Passes Combined Frequency of each N-Gram through Context to Reducer
        frequency.set(freq_sum);
        context.write(key, frequency);
    }

}