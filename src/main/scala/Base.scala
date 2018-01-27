import scala.collection.mutable

object IfElse {
  def main(args: Array[String]): Unit = {
    ifelse("a")
  }

  def ifelse(p: Any) = {
    //println(p.asInstanceOf[Int])
    val maybeString: Option[String] = matches(p.asInstanceOf[String])
    println(maybeString.get)

    if (p.isInstanceOf[String]) {   //类型判断
      println(matchCase(p.asInstanceOf[String])) //强转为String
    } else {
      println(partialMatchDouble(p.asInstanceOf[Int])) //强转为Int
    }
  }

  /**
    * 偏函数
    *
    * @return
    */
  def partialMatchDouble: PartialFunction[Int, Int] = {
    case 1 => 2 * 1
    case 2 => 2 * 2
    case _ => 0
  }

  /**
    * Option表示该结果返回值可能为空(None)
    * 如果不是空,必须将结果用Some通过验证,结果使用get获取返回值
    * @param x
    * @return
    */
  def matches(x: String): Option[String] = x match {
    case "a" => val r = x + "_matched"; println(s"ok->$r"); Some(r)
    case "b" => Some(x + "_matched")
    case "c" => Some(x + "_matched")
    case "d" => Some(x + "_matched")
    case _ => return None
  }

  /**
    * 匹配
    *
    * @param x
    * @return
    */
  def matchCase(x: String): String = {
    x match {
      case "a" => val r = x + "_matched"; println(s"ok->$r"); r
      case "b" => x + "_matched"
      case "c" => x + "_matched"
      case "d" => x + "_matched"
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

    //println(1 + 1)


  }
}


