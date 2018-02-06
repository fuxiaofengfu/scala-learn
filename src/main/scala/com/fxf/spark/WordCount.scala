package com.fxf.spark

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

class WordCount {

  def wordCount: Unit = {
    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(conf)
    val text: RDD[String] = sc.textFile("/Users/xiaofengfu/Downloads/fuxiaofeng.txt")
    val flat: RDD[String] = text.flatMap((x) => {
      var list: List[String] = List[String]()
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
    val strings: Array[String] = values.collect() //action 操作
    //strings.foreach(println(_))
    val l1: Long = System.currentTimeMillis()
    println("无缓存数据执行时间", l1 - l)

    val aaa: Array[String] = values.collect() //action 操作
    //aaa.foreach(println(_))
    val l2: Long = System.currentTimeMillis()

    println("有缓存执行时间-》", l2 - l1)
  }

  def aggFa(a: Int, b: Int): Int = {
    println(a, b)
    a + b
  }

  def ttt(x: Int = 0): Unit = {
    println(x)
  }
}

class WordCount1 {

  def wordCount(): Unit = {
    val context: SparkContext = new SparkContext("local", "f")

    //rdd.take(1).foreach(println(_))
    //单个文件作为输入
    val value: RDD[String] = context.textFile("file/a.txt")
    val value2: RDD[String] = value.flatMap(_.split(" "))
    val value3: RDD[(String, Int)] = value2.map((_, 1))

    val value5: RDD[(String, Iterable[Int])] = value3.groupByKey()
    val value6: RDD[(String, Int)] = value5.mapValues(_.sum)
    value6.foreach(println(_))

    val value4: RDD[(String, Int)] = value3.reduceByKey(_ + _)
    value4.foreach(println(_))

    //整个目录作为输入
    val rdd: RDD[(String, String)] = context.wholeTextFiles("file")
    val value1: RDD[String] = rdd.map(_ _2)
    val value7: RDD[String] = value1.flatMap(x => {
      if (x.contains("\n")) {
        val str: String = x.replace("\n", "") //去掉 "\n"换行符
        str.split(" ")
      } else {
        x.split(" ")
      }
    })
    val value8: RDD[(String, Int)] = value7.map((_, 1))
    val value9: RDD[(String, Int)] = value8.reduceByKey(_ + _)
    val value10: RDD[String] = value9.map(x => x._1 + "\t" + x._2)

    val outPath = "file/out"
    MyWCImplicit.deletePath(outPath)
    value10.saveAsTextFile(outPath)
    value6.saveAsTextFile(outPath) //一个数据集只能单独输出到不同目录
  }
}

object RunTest {
  def main(args: Array[String]): Unit = {
    val count: WordCount1 = new WordCount1()
    count.wordCount
  }
}

object MyWCImplicit {
  def deletePath(path: String) = {
    val system: FileSystem = FileSystem.get(new Configuration())
    if (system.exists(new Path(path))) {
      system.delete(new Path(path), true)
    }
  }
}


