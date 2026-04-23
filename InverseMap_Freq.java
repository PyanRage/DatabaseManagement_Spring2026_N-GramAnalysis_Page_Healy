package org.example;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

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

        // Passes (Frequency, N-Gram) to Reducer, InverseMapping the <K, V> pair
        // Allows reference/sorting by frequency, so it can return the most common N-Grams found
        context.write(frequency, ngram);
    }
}
