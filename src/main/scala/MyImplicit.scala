package com.fxf

object MyImplicit {

  //给字符串对象添加方法implicitChange
  implicit def implicitChange(x: String) = (y:String) => x + y

  // 这里给string对象添加了对象Operate的所有方法,因为返回的就是一个Operate对象
  implicit def implicitChange1(x: String) = new Operate(x)
}

class Operate(val x: String) {
  def read(y:String): Unit = {
    println(s"read......$x...$y")
  }

  def read2(y:String): Unit = {
    println(s"read......$x...$y")
  }
}

object MyImplicitTest {

  def main(args: Array[String]): Unit = {
    implicit val aaa:String = "ababer"
    test2("aaaa")
  }

  def test1(): Unit ={
    import com.fxf.MyImplicit.implicitChange
    val c  = implicitChange("123456")("11")   //当有两个相同参数的隐式转换类型时,需要指定使用具体的隐式
    // val c  = "123456"("11")  这里报错
    println(c)
    import com.fxf.MyImplicit.implicitChange1
    "aaa" read "ccc"
    "aaa" read2 "ccc"
  }

  def test2(x:String)(implicit y:String = "test2 y "): Unit= {
    implicit  val zzz:String = "zzzz"   //这里定义的隐式值不起作用
    println(x + y)
  }
}

