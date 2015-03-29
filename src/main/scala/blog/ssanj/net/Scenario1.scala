package blog.ssanj.net

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

/**
 * Futures run immediately.
 * They do not depend on the for comprehension to run.
 */
object Scenario1 extends Iterate {
  def main(args: Array[String]) {
    scenario1()
  }

  private def scenario1() {
    val f1: Future[Int] = Future {
      iterate("f1", 10)
    }
    val f2: Future[Int] = Future {
      iterate("f2", 2)
    }

    Thread.sleep(10000) //sleep for 10 seconds

    println("before comprehension")

    val f3:Future[Int] =  for {
      x <- {println("f1 ->"); f1}
      y <- {println("f2 ->"); f2}
    } yield x + y

    f3.onComplete(_ => println("f3 done"))

    println("after comprehension")

    val result = Await.result(f3, Duration(1, TimeUnit.MINUTES))

    println(s"done with $result")
  }
}


