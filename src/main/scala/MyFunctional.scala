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
    //既作为参数也作为返回结果
    val func: (Int => Int) => Int => Int => Int => Int = { (a: Int => Int) => a => a => a => a * 10 }
    //println(func((x: Int) => x * 6)(5))
  }

  def testCurrying: Unit = {
    def sum(f: Int => Int)(a: Int, b: Int): Int = a + b
      //if (a > b) a + b else f(a) + sum(f)(a + 1, b)

    val i: Int = sum(x => x)(5,3)
    println(i)

    val intToInt: Int => Int = (x:Int) => x * 10  // 等号左边是函数定义，右边是函数实现，一一对应

    def currying(x:Int)(y:String) = x * y.toInt
    val stringToInt: String => Int = currying(3)
    println(stringToInt("2"))
  }
}