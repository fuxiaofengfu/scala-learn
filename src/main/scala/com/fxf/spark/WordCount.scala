package com.fxf.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

class WordCount {

  def wordCount: Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val text: RDD[String] = sc.textFile("/Users/xiaofengfu/Downloads/fuxiaofeng.txt")
    val flat: RDD[String] = text.flatMap((x) => {
      var list:List[String] = List[String]()
      x.toCharArray.foreach((str) => {
        list = str.toString :: list
      })
      list
    })
    //val flat: RDD[String] = text.flatMap(_.split(" "))
    val map1: RDD[(String, Int)] = flat.map(word => (word, 1))
    //val value1: RDD[(String, Int)] = map1.reduceByKey(_ + _)
    val map2: RDD[(String, Iterable[Int])] = map1.groupByKey()
    val value: RDD[(String, Int)] = map2.mapValues((a: Iterable[Int]) => a.toList.size)
    val values: RDD[String] = value.map(x => x._1 + "  " + x._2)
    //values.cache()
    val strings: Array[String] = values.collect()
    strings.foreach(println(_))
  }

  def aggFa(a: Int, b: Int): Int = {
    println(a, b)
    a + b
  }

  def ttt(x: Int = 0): Unit = {
    println(x)
  }
}

object RunTest {
  def main(args: Array[String]): Unit = {
    val count: WordCount = new WordCount()
    count.wordCount
  }
}


