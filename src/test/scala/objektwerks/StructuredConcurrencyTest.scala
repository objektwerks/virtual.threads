package objektwerks

import java.time.Instant
import java.util.concurrent.StructuredTaskScope

import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success, Using}

import FileLineCountTask.*

final class StructuredConcurrencyTest extends AnyFunSuite:
  test("join"):
    val lines = Using( StructuredTaskScope.ShutdownOnFailure() ) { scope =>
      val aLines = scope.fork( () => FileLineCountTask("./data/data.a.csv").call() )
      val bLines = scope.fork( () => FileLineCountTask("./data/data.b.csv").call() )
      scope.join()
      scope.throwIfFailed()
      aLines.get() + bLines.get()
    }
    lines match
      case Success(count) => assert(count == expectedLineCount)
      case Failure(error) => fail(error.getMessage)

  test("join until"):
    Using( StructuredTaskScope.ShutdownOnFailure() ) { scope =>
      val futures = tasks.map( task => scope.fork( () => task.call() ) )
      scope.joinUntil( Instant.now().plusMillis(3000) )
      scope.throwIfFailed()
      futures.map( future => future.get() ).sum
    }.fold( error => fail(error.getMessage), lines => assert(lines == expectedLineCount) )

  test("race"):
    Using( StructuredTaskScope.ShutdownOnSuccess[Int]() ) { scope =>
      tasks.foreach( task => scope.fork( () => task.call() ) )
      scope.joinUntil( Instant.now().plusMillis(3000) )
      scope.result()
    }.fold( error => fail(error.getMessage), lines => assert(lines == 270_562 || lines == 270_397) )