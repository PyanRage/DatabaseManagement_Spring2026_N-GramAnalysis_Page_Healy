package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*  Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
 */

// TODO: CHECK INPUT DATA TYPES
public class Map_NGrams extends Mapper<LongWritable, GenericRecord, Text, IntWritable> {

    // Take Input Strings, Break into NGrams, Pass to Context

    // Variables for passing through Context to Reducer
    private Text ngram = new Text();
    private IntWritable one = new IntWritable(1);

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // Gets the value fo the "text" field of the Parquet file
        String text = value.get("text").toString();
        text = text.toLowerCase().replaceAll("[^a-z\\s]", " ");
        String[] parsed = text.split("\\s+");

        // Creates 3-Grams
        for (int i = 0; i < (parsed.length - 2); i++) {
            // Takes indices [i to i+2] to create the 3-Gram
            String setting = parsed[i] + " " + parsed[i + 1] + " " + parsed[i + 2];

            // Sets the Text variable for passing into the Context
            ngram.set(setting);

            // Writes 3-Gram & 1 frequency counter to the Frequency Reducer
            context.write(ngram, one);
        }

        /* DELETE COMMENTS TO DO 2-GRAM MAPPING
        // Creates 2-Grams
        for (int i = 0; i < (parsed.length - 1); i++) {
            // Takes indices [i and i+1] to create the 2-Gram
            String setting = parsed[i] + " " + parsed[i + 1];

            // Sets the Text variable for passing into the Context
            ngram.set(setting);

            // Writes 2-Gram & 1 frequency counter to the Frequency Reducer
            context.write(ngram, one);
        }
         */ // DELETE COMMENTS TO DO 2-GRAM MAPPING

    }
}
