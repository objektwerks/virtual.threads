package objektwerks

import java.util.concurrent.StructuredTaskScope

import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success, Using}

import FileLineCountTask.*

final class StructuredConcurrencyTest extends AnyFunSuite:
  test("join"):
    val lines = Using( StructuredTaskScope.open[Int]() ) { scope =>
      val aLines = scope.fork( () => FileLineCountTask("./data/data.a.csv").call() )
      val bLines = scope.fork( () => FileLineCountTask("./data/data.b.csv").call() )
      scope.join()
      aLines.get() + bLines.get()
    }
    lines match
      case Success(count) => assert(count == expectedLineCount)
      case Failure(error) => fail(error.getMessage)