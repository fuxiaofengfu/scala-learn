class ApplyUnapply {

  //   def this(x:String)={
  //     this()
  //     println("init .......")
  //   }
}

object ApplyUnapply {

  //使用该伴生对象时，自动调用apply方法
  //动态参数构造, >= 0
  def apply(x: String*): ApplyUnapply = {
    println(x); new ApplyUnapply()
  }

  //Option作为方法返回值时，代表对象可能为空
  // unapply方法主要用于模式匹配
  def unapply(arg: ApplyUnapply): Option[ApplyUnapply] = {
    if (null == arg) {
      return None
    }
    Some(arg)
  }

  def getByInt(x: ApplyUnapply) = {
    println(s"get by int $x")
  }

  implicit def converToApplyUnapply(x: Int): ApplyUnapply = ApplyUnapply(x.toString)
}

object Test {
  def main(args: Array[String]): Unit = {

    // object静态类在使用的时候默认调用apply方法
    val unit = ApplyUnapply("init.....","ssdf","fdgt")
    val unit2 = ApplyUnapply()
    println(unit2)

    //这里使用了隐式转换
    val unit1: Unit = ApplyUnapply.getByInt(1)

    //模式匹配不支持隐式转换
    unit match {
      // 这里模式匹配自动调用unapply方法去匹配
      case ApplyUnapply(unit) => println(s"match ApplyUnapply obj ....$unit")
      case _ => println("no matches")
    }
  }
}