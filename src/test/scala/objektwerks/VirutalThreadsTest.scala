package objektwerks

import java.time.Instant
import java.util.concurrent.{Callable, Executors, Future}
import jdk.incubator.concurrent.StructuredTaskScope

import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.tailrec
import scala.io.{Codec, Source}
import scala.jdk.CollectionConverters.*
import scala.util.{Failure, Success, Try, Using}

import objektwerks.FileLineCountTask
class VirtualThreadsTest extends AnyFunSuite:
  val tasks = List( FileLineCountTask("./data/data.a.csv"), FileLineCountTask("./data/data.b.csv") )
  val expectedLineCount = 540_959

  test("virtual threads submit") {
    Using( Executors.newVirtualThreadPerTaskExecutor() ) { executor =>
      val aFuture = executor.submit( () => FileLineCountTask("./data/data.a.csv").call() )
      val bFuture = executor.submit( () => FileLineCountTask("./data/data.b.csv").call() )
      aFuture.get() + bFuture.get()
    }.fold( error => fail(error.getMessage()), lines => assert(lines == expectedLineCount) )
  }

  test("virtual threads invoke all") {
    Using( Executors.newVirtualThreadPerTaskExecutor() ) { executor =>
      executor.invokeAll( tasks.asJava ).asScala.map( future => future.get() ).sum
    }.fold( error => fail(error.getMessage()), lines => assert(lines == expectedLineCount) )
  }