package org.example;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// TODO: NEEDS TO GET RIGHT OUTPUT DATA TYPES
public class Reduce_TopK extends Reducer<Text, IntWritable, TYPE, TYPE> {

    // NEEDS TO DO TOP-K RANKING THINGS HERE, YEAH

}
