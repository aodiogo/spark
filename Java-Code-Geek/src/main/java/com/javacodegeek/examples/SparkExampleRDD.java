package com.javacodegeek.examples;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkExampleRDD {

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("No file provided");
            System.exit(0);
        }

        String filename = args[0];
        // configure spark
        SparkConf sparkConf = new SparkConf().setAppName("Word Count").setMaster("local").set("spark.executor.memory","2g");

        // start a spark context
        JavaSparkContext sc = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sc.textFile(filename);

        JavaRDD<String> wordsList = inputFile.flatMap(content -> Arrays.asList(content.split(" ")).iterator());

        JavaPairRDD<String, Integer> wordCount = wordsList.mapToPair(t -> new Tuple2<>(t, 1)).reduceByKey((x, y) -> (int) x + (int) y);

        wordCount.saveAsTextFile("Word Count");


    }
}
