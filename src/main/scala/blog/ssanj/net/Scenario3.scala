package blog.ssanj.net

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Promise, Await, Future}
import scala.util.{Success, Failure}

object Scenario3 extends Iterate {

  def main(args: Array[String]) {
   scenario3()
  }

  /**
   * Futures defined with zip within a for-comprehension run asynchronously.
   */
  private  def scenario3(): Unit = {
    println("before comprehension")

    val f3:Future[Int] = for {
      (x, y) <- Future { println("f1 ->"); iterate("f1", 10) } zip Future {
        println("f2 ->"); iterate("f2", 2) }
    } yield x + y

    f3.onComplete(_ => println("f3 done"))

    println("after comprehension")

    val result = Await.result(f3, Duration(1, TimeUnit.MINUTES))

    println(s"done with $result")
  }
}
