import scala.collection.mutable

object MyList {

  def main(args: Array[String]): Unit = {
    val clazz: Class[_ <: Unit] = test.getClass
    val unit = fn(2)
    wordCount
  }

  val fn: Int => Unit = (x: Int) => println(x * 10)

  def test = {   //自动判断返回类型,如果有，则返回最后一句的类型或者为Unit
    println("22" ++: ("b"))

  }

  def op = {
    val linkList = mutable.LinkedList(1,2,3,4)
    val linkList1 = mutable.LinkedList("a","b","c","d")
    val list = List(1,2,3,4,5)
    val list1 = List(1,2,3,4,5)

    val new0 = list ::: list1   //集合链接,必须同类型
    val new1 = linkList ++ list  //返回LinkedList类型  可以是不用类型
    val new5 = list ++ linkList  // 返回 List类型  可以是不同类型
    val new4 =  linkList1 :: list  //在集合头部追加,类似 +:, ::可用正则
    val new2 = "a" +: list  //集合头部追加
    val new3 = list :+ 1  //集合尾部追加
    println(s"new2 => $new2")
    println(s"new4 => $new4")
    println(s"new0 => $new0")
    println(s"new1 => $new1")
    println(s"new5 => $new5")
    println(s"new3 => $new3")
    println(s"list => $list")
  }

  def wordCount(): Unit = {

    val list = List(("a", "b", "c", "d"), ("c", "e", "f", "e"), ("a", "b", "g", "d"), ("d", "g", "f", "d"))
    val tup = ("a", "b", "c", "d")
    val arr = for (i <- tup.toString().split(",")) yield i
    val a = list.flatMap(_.productIterator)
    println(s"flatMap => $a")
    val map = a.map(_ -> 1)
    println(map)
    val group = map.groupBy(_ _1)
    println(group)
    val mapvs = group.mapValues(_.size)
    println(s"mapValues => $mapvs")
    val resul = mapvs.toList.sortBy(_ _2)
    println(resul)
    var lll = List(1, 2, 3, 4, 5, 6)
    var (aaa, bbb) = lll partition (_ > 2)
    println(aaa)
    println(bbb)
  }
}

