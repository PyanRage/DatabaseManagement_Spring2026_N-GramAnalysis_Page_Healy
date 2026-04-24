package org.example;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
    Author: Ryan Page, Michael Healy
    Date: 04/23/2026
    Description: Inverse Mapper for MapReduce, Swaps N-Gram/Frequency to Frequency/N-Gram
    Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
*/

// Swaps Key-Value pairs to allow Ranking by Frequency Count
public class InverseMap_Freq extends Mapper<LongWritable, Text, IntWritable, Text> {

    // Variables for passing through Context
    private Text ngram = new Text();
    private IntWritable frequency = new IntWritable();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // Splits (value) into [N-Gram] [Frequency]
        String[] split = value.toString().split("\t");

        ngram.set(split[0]);
        frequency.set(Integer.parseInt(split[1]));

        // Passes (Frequency, N-Gram) to Reducer
        context.write(frequency, ngram);
    }
}
