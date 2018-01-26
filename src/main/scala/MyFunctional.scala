object MyFunctional {

  def main(args: Array[String]): Unit = {
    testCurrying
  }

  def testFunc(): Unit = {
    val a = (1 to 10).map("~" * _)

    //函数作为参数
    def convertIntToString(f: (Int) => String) = f(4)

    val str: String = convertIntToString((x: Int) => x + "ss")

    //函数作为返回结果
    def multiplyBy(factor: Double) = (x: Double) => factor * x

    var fu = (x: Int, y: Int) => x * y
    println(fu(1, 2))
    val intToInt: Int => (String, String) => Int = (x: Int) => (a: String, b: String) => a.toInt + x + b.toInt
    val stringToInt = intToInt(1)("2", "222")
    println(stringToInt)
    //柯里化
    val func: (Int => Int) => (Int => (Int => (Int => Int))) = { (a: Int => Int) => a => a => a => a * 10 }
    //println(func((x: Int) => x * 6)(5))
  }

  def testCurrying: Unit = {
    def sum(f: Int => Int)(a: Int, b: Int): Int = a + b
    //if (a > b) a + b else f(a) + sum(f)(a + 1, b)

    val i: Int = sum(x => x)(5, 3)
    println(i)

    val intToInt: Int => Int = (x: Int) => x * 10 // 等号左边是函数定义，右边是函数实现，一一对应

    val intToStringToString: Int => String => String = (x: Int) => (y: String) => y * x
    val str: String = intToStringToString(2)("5")
    println(str)

    //柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
    //新的函数返回一个以原有第二个参数为参数的函数。

    //原函数:function
    //柯里化:currying(对function柯里化)
    //柯里化主要用于减少函数参数个数,而且后面的参数可以通过前面的参数推导

    def function(x: Int)(y: String) = y * x
    val curr: Int => String => String = function _  //柯里化
    println(curr(1)("s"))
    val cc: String = function(3)("a")
    println(cc)

    val currying: Int => (String => String) = (x: Int) => (y: String) => y * x  //柯里化
    println(currying(2)("b"))
  }
}