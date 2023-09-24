package objektwerks

import java.util.concurrent.Executors

import org.scalatest.funsuite.AnyFunSuite

import scala.jdk.CollectionConverters.*
import scala.util.Using

import FileLineCountTask.*

final class VirtualThreadsTest extends AnyFunSuite:
  test("submit"):
    Using( Executors.newVirtualThreadPerTaskExecutor() ) { executor =>
      val aFuture = executor.submit( () => FileLineCountTask("./data/data.a.csv").call() )
      val bFuture = executor.submit( () => FileLineCountTask("./data/data.b.csv").call() )
      aFuture.get() + bFuture.get()
    }.fold( error => fail(error.getMessage), lines => assert(lines == expectedLineCount) )

  test("invoke all"):
    Using( Executors.newVirtualThreadPerTaskExecutor() ) { executor =>
      executor.invokeAll( tasks.asJava ).asScala.map( future => future.get() ).sum
    }.fold( error => fail(error.getMessage), lines => assert(lines == expectedLineCount) )