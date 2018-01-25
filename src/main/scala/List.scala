class List {

  def main(args: Array[String]): Unit = {
    val list = List(("a", "b", "c", "d"), ("c", "e", "f", "e"), ("a", "b", "g", "d"), ("d", "g", "f", "d"))
    val tup = ("a", "b", "c", "d")
    val arr = for (i <- tup.toString().split(",")) yield i
    val a = list.flatMap(_.productIterator)
    val map = a.map(_->1)
    println(map)
    val group = map.groupBy(_ _1)
    println(group)
    val mapvs = group.mapValues(_.size)
    println(mapvs)
    val resul = mapvs.toList.sortBy(_ _2)
    println(resul)
    var lll = List(1,2,3,4,5,6)
    var (aaa,bbb) = lll partition( _ > 2)
    println(aaa)
    println(bbb)
  }
}

