package blog.ssanj.net

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Scenario5 extends Iterate {

  def main(args: Array[String]) {
   scenario5()
  }

  /**
   * Futures defined with zip within a for-comprehension run asynchronously.
   */
  private  def scenario5(): Unit = {
    println("before comprehension")

    val f4:Future[Int] = for {
      a <- Future {println("f1 ->"); iterate("f1", 5) }
      (x, y) <- Future { println("f2 ->"); iterate("f2", 10) } zip Future {
        println("f3 ->"); iterate("f3", 2) }
    } yield a + x + y

    f4.onComplete(_ => println("f4 done"))

    println("after comprehension")

    val result = Await.result(f4, Duration(1, TimeUnit.MINUTES))

    println(s"done with $result")
  }
}
