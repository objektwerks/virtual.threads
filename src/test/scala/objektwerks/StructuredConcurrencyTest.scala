package objektwerks

import java.time.Instant
import java.util.concurrent.{Callable, Executors, Future}
import jdk.incubator.concurrent.StructuredTaskScope

import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.tailrec
import scala.jdk.CollectionConverters.*
import scala.util.{Failure, Success, Try, Using}

import objektwerks.FileLineCountTask
class StructuredConcurrencyTest extends AnyFunSuite:
  val tasks = List( FileLineCountTask("./data/data.a.csv"), FileLineCountTask("./data/data.b.csv") )
  val expectedLineCount = 540_959

  test("structured concurrency join") {
    val lines = Using( StructuredTaskScope.ShutdownOnFailure() ) { scope =>
      val alines = scope.fork( () => FileLineCountTask("./data/data.a.csv").call() )
      val blines = scope.fork( () => FileLineCountTask("./data/data.b.csv").call() )
      scope.join()
      scope.throwIfFailed()
      alines.resultNow() + blines.resultNow()
    }
    lines match
      case Success(count) => assert(count == expectedLineCount)
      case Failure(error) => fail(error.getMessage)
  }

  test("structured concurrency join until") {
    Using( StructuredTaskScope.ShutdownOnFailure() ) { scope =>
      val futures = tasks.map( task => scope.fork( () => task.call() ) )
      scope.joinUntil( Instant.now().plusMillis(3000) )
      scope.throwIfFailed()
      futures.map( future => future.resultNow() ).sum
    }.fold( error => fail(error.getMessage), lines => assert(lines == expectedLineCount) )
  }

  test("structured concurrency race") {
    Using( StructuredTaskScope.ShutdownOnSuccess[Int]() ) { scope =>
      tasks.foreach( task => scope.fork( () => task.call() ) )
      scope.joinUntil( Instant.now().plusMillis(3000) )
      scope.result()
    }.fold( error => fail(error.getMessage), lines => assert(lines == 270_562 || lines == 270_397) )
  }