package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Combiner_Frequencies extends Reducer<Text, IntWritable, Text, IntWritable> {

    // COMBINER TO REDUCE NETWORK USAGE, TO REDUCE MEMORY USAGE

    private IntWritable frequency = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        // Initializes Frequency Sum
        int freq_sum = 0;

        for (IntWritable val : values) {
            // Combines Frequency Values
            freq_sum += val.get();
        }

        // Passes Combined Frequency of each N-Gram through Context to Reducer
        frequency.set(freq_sum);
        context.write(key, frequency);
    }

}