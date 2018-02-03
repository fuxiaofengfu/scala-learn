package com.fxf.spark

import java.util.Date

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

    //因为后面调用了两次collect，即执行两次action操作,
    // 这里将数据缓存可供第二次执行rdd action的复用第一次执行collect 缓存的结果
    //缓存要起作用必须在有rdd action操作之前调用缓存
    //rdd action操作才触发了dag的执行,其它rdd的操作都是懒加载的
    values.cache()

    println("未执行collect前..............")
    val l: Long = System.currentTimeMillis()
    val strings: Array[String] = values.collect()  //action 操作
    //strings.foreach(println(_))
    val l1: Long = System.currentTimeMillis()
    println("无缓存数据执行时间",l1 - l)

    val aaa: Array[String] = values.collect()  //action 操作
    //aaa.foreach(println(_))
    val l2: Long = System.currentTimeMillis()

    println("有缓存执行时间-》",l2 - l1)
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


