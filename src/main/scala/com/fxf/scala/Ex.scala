package com.fxf.scala

class Ex {

}

case class Ex1(){

  def ex(): Unit ={
    println("ex.......")
  }
}

object ExRun{
  def main(args: Array[String]): Unit = {
    val ex: Ex1 = new Ex1()
    ex.ex()
  }
}
