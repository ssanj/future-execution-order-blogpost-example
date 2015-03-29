package blog.ssanj.net

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object Scenarioz extends Iterate {
  def main(args: Array[String]) {
    scenario3()
  }

  /**
   * Futures started outside the for-comprehension will start running immediately. Futures defined as part of the
   * for-comprehension will only run after its dependent futures run first.
   */
  private  def scenario3():Unit = {
    val f1: Future[Int] = Future {
      iterate("f1", 10)
    }
    val f2: Future[Int] = Future {
      iterate("f2", 2)
    }

    Thread.sleep(1000) //sleep for 2 seconds

    println("before comprehension")

    val f4 = for {
      x <- {println("f1 ->"); f1 }
      y <- {println("f2 ->");f2}
      z <- {println("f3 ->"); Future { iterate("f3", 4) }}
    } yield x + y + z

    f4.onComplete(_ => println("f4 done"))

    println("after comprehension")

    val result = Await.result(f4, Duration(1, TimeUnit.MINUTES))

    println(s"done with $result")
  }
}
