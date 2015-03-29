package blog.ssanj.net

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object Scenario2 extends Iterate {

  def main(args: Array[String]) {
   scenario2()
  }

  /**
   * Futures defined within a for-comprehension run in order of definition.
   * There is no parallelism.
   */
  private def scenario2(): Unit = {
    println("before comprehension")

    val f3:Future[Int] = for {
      x <- Future { println("f1 ->"); iterate("f1", 10) }
      y <- Future { println("f2 ->"); iterate("f2", 2) }
    } yield x + y

    f3.onComplete(_ => println("f3 done"))

    println("after comprehension")

    val result = Await.result(f3, Duration(1, TimeUnit.MINUTES))

    println(s"done with $result")
  }

}
