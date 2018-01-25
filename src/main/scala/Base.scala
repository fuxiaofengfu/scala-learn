object IfElse {
  def main(args: Array[String]): Unit = {
    println(matchCase("a"))
    println(partialMatchDouble(2))
  }

  /**
    * 翩函数
    * @return
    */
  def partialMatchDouble:PartialFunction[Int,Int]={
    case 1 => 2 * 1
    case 2 => 2 * 2
    case _ => 0
  }

  /**
    * 匹配
    * @param x
    * @return
    */
  def matchCase(x: String): String = {
    x match {
      case "a" =>  val r = x + "_matched";println(s"ok->$r");r
      case "b" =>  x + "_matched"
      case "c" =>  x + "_matched"
      case "d" =>  x + "_matched"
      case _ => return "nothing matches"
    }
  }
}

object ForEach {
  def main(args: Array[String]): Unit = {

  }
}

object Calculate {
  def main(args: Array[String]): Unit = {

  }
}


