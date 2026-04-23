package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce_Frequencies extends Reducer<Text, IntWritable, Text, IntWritable> {

    // Aggregate Frequencies for each N-Gram instance

    private IntWritable frequency = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Initializes Frequency Sum
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