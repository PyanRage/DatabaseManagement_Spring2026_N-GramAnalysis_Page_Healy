package nGramAnalysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/*
    Author: Ryan Page, Michael Healy
    Date: 04/23/2026
    Description: Descending Comparator for MapReduce, Sorts N-Grams from Most Frequent to Least Frequent
    Source Code Adapted from https://github.com/stepthom/CountNGrams/blob/master/src/CountNGrams.java,
        https://www.jesse-anderson.com/2013/07/cloudera-quickstart-vm-and-eclipse/,
        https://medium.com/data-science/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7,
        and Generated from ChatGPT
*/

// Compares each Frequency/N-Gram, Makes Most Frequent N-Gram as first result
public class DescendingComparator extends WritableComparator {

    public DescendingComparator() {
        super(IntWritable.class, true);
    }

    // Compares two Ints, Makes greater Int higher in order
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        IntWritable gram1 = (IntWritable) a;
        IntWritable gram2 = (IntWritable) b;

        return -1 * gram1.compareTo(gram2);
    }
}
