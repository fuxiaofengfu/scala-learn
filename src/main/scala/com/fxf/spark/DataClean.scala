package com.fxf.spark

import org.apache.commons.lang3.time.DateUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

class DataClean {

}

object DataClean {

  //数据行的分割符
  private final val LINE_SPERATOR = "\001"
  //数据列的分割符
  private final val DATA_SPERATOR = "\t"

  def main(args: Array[String]): Unit = {
    cleanData
  }

  def cleanData: Unit = {
    val data: Array[String] = getData
    val da: mutable.HashMap[String, String] = groupByAccount(data)
    val result: Array[String] = handleEveryAccount(da)
    result.foreach(println(_))
  }

  def getData: Array[String] = {
    val conf = new SparkConf().setAppName("cleandata").setMaster("local")
    val sc = new SparkContext(conf)
    val text: RDD[String] = sc.textFile("cleandata/cc.txt")
    println(">>>>>>>>>>>>>>>>获取到的原始数据")
    val flat: RDD[String] = text.map(ele => {
      println(ele)
      ele
    })
    val strings: Array[String] = flat.collect()
    strings
  }

  def groupByAccount(data: Array[String]): mutable.HashMap[String, String] = {

    val dataMap = new mutable.HashMap[String, String]()
    data.foreach(str => {
      val strings: Array[String] = str.split(DATA_SPERATOR)
      val account = strings.apply(0)
      var newStr = str
      if (dataMap.contains(account)) {
        val s: String = dataMap.get(account).get
        newStr = s + LINE_SPERATOR + str
      }
      dataMap.put(account, newStr)
    })
    dataMap
  }

  def handleEveryAccount(data: mutable.HashMap[String, String]): Array[String] = {

    var list = List[String]()
    data.keySet.foreach(key => {
      val str: String = data.get(key).get
      list = list ++ handleNullField(str)
    })
    list.toArray
  }

  def handleNullField(str: String): Array[String] = {

    var dataResult = List[String]()

    //map key为字符串字段数据,value为时间long值
    val nullMap = new mutable.HashMap[String, Long]()
    val notNullMap = new mutable.HashMap[String, Long]()
    str.split(LINE_SPERATOR).foreach(s => {
      val data: Array[String] = s.split(DATA_SPERATOR)
      val dateStr = data.apply(1)
      val timeZone = data.apply(2)
      if (null == timeZone || timeZone.length <= 0 || "null".equalsIgnoreCase(timeZone)) {
        nullMap.put(s, strToDateLong(dateStr))
      } else {
        dataResult = dataResult :+ s
        notNullMap.put(s, strToDateLong(dateStr))
      }
    })
    //按照时间排序
    val nullSeq: Seq[(String, Long)] = nullMap.toSeq.sortBy(_._2)
    val notNull: Seq[(String, Long)] = notNullMap.toSeq.sortBy(_._2)

    //将最近的timeZone值放入timeZone为null的里面
    nullSeq.foreach(x => {
      val nullArr: Array[String] = x._1.split(DATA_SPERATOR)
      var abs = Long.MaxValue;
      val long1 = strToDateLong(nullArr.apply(1))
      var timeZone = ""
      notNull.foreach(y => {
        val notnullArr: Array[String] = y._1.split(DATA_SPERATOR)
        val timeZone2: String = notnullArr.apply(2)
        val long2 = strToDateLong(notnullArr.apply(1))
        val abs1 = long2 - long1
        if (abs > abs1.abs || abs == Long.MaxValue) {
          abs = abs1
          timeZone = timeZone2
        }
      })
      nullArr.update(2, timeZone)
      dataResult = dataResult :+ nullArr.mkString(DATA_SPERATOR)
    })
    dataResult.toArray
  }


  def strToDateLong(str: String): Long = {
    if (null == str || str.length <= 0 || "null".equalsIgnoreCase(str)) {
      return 0
    }
    DateUtils.parseDateStrictly(str, "yyyy-MM-dd HH:mm:ss").getTime
  }

}
