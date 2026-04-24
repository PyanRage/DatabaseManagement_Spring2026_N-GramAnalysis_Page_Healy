package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
    Author: Ryan Page, Michael Healy
    Date: 04/23/2026
    Description: Final Reducer for MapReduce, Returns Top-K Ranked N-Grams
    Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
*/

// Returns Top-K Ranked N-Grams by their Frequency
public class Reduce_TopK extends Reducer<IntWritable, Text, IntWritable, Text> {

    // Initializes global variable
    int k = 0;

    @Override
    public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        for (Text val : values) {
            // While K is below the specified K value
            if (k >= 50) { // CHANGE INT TO DESIRED K VALUE
                break;
            }

            // Writes Frequency/N-Gram to final Output File
            context.write(key, val);
            k++;
        }
    }

}


