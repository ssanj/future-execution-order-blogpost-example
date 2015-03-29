package blog.ssanj.net

trait Iterate {

  final def iterate(name:String, n:Int): Int = {
    println(s"defining $name")
    (1 to n).foreach { n =>
      println(s"$name sleeping for item: $n")
      Thread.sleep(250)
    }

    println(s"$name done")
    n
  }
}
