package blog.ssanj.net

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

object Scenario4 extends Iterate {

  def main(args: Array[String]) {
   scenario4()
  }

  /**
   * Futures defined within a for-comprehension run in order of definition.
   * There is no parallelism.
   */
  private def scenario4(): Unit = {
    println("before comprehension")

    val f3:Future[Int] = for {
      (x, y) <- zip2(Future { println("f1 ->"); iterate("f1", 10) })(Future {println("f2 ->"); iterate("f2", 2) })
    } yield x + y

    f3.onComplete(_ => println("f3 done"))

    println("after comprehension")

    val result = Await.result(f3, Duration(1, TimeUnit.MINUTES))

    println(s"done with $result")
  }

  private def zip2[T,U](one: Future[T])(two: => Future[U])(implicit ec:ExecutionContext): Future[(T, U)] = {
    val p = Promise[(T, U)]()
    one.onComplete {
      case f: Failure[_] => p complete f.asInstanceOf[Failure[(T, U)]]
      case Success(s) => two onComplete { c => p.complete(c map { s2 => (s, s2) }) }
    }
    p.future
  }

}
