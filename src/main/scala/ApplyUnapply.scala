class ApplyUnapply {

//   def this(x:String)={
//     this()
//     println("init .......")
//   }
}

object ApplyUnapply{

  //使用该伴生对象时，自动调用apply方法
  def apply(x:String): ApplyUnapply = {println(x);new ApplyUnapply()}

  //Option作为方法返回值时，代表对象可能为空
  // unapply方法主要用于模式匹配
  def unapply(arg: ApplyUnapply): Option[ApplyUnapply] =  {
    if(null == arg){
      return None
    }
    Some(arg)
  }
}

object Test{
  def main(args: Array[String]): Unit = {

    // object静态类在使用的时候默认调用apply方法
    val unit = ApplyUnapply("init.....")
    println(unit)

    unit match {
      // 这里模式匹配自动调用unapply方法去匹配
      case ApplyUnapply(unit) => println(s"match ApplyUnapply obj ....$unit")
      case _ => println("no matches")
    }
  }
}