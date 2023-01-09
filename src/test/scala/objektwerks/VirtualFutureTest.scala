package objektwerks

import java.util.concurrent.Executors

import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import FileLineCountTask.*

final class VirtualFutureTest extends AsyncFunSuite with Matchers:
  implicit override val executionContext: ExecutionContext = ExecutionContext.fromExecutor( Executors.newVirtualThreadPerTaskExecutor() )

  test("parallel") {
    val aFuture = Future { FileLineCountTask("./data/data.a.csv").call() }
    val bFuture = Future { FileLineCountTask("./data/data.b.csv").call() }
    for
      aLines <- aFuture
      bLines <- bFuture
    yield aLines + bLines shouldBe expectedLineCount
  }

  test("zip") {
    Future { FileLineCountTask("./data/data.a.csv").call() } zip
    Future { FileLineCountTask("./data/data.b.csv").call() } map
    { (aLines, bLines) => aLines + bLines shouldBe expectedLineCount }
  }

  test("recover") {
    Future { FileLineCountTask("./data/data.a.csv").call() } zip
    Future { FileLineCountTask("./data/data.c.csv").call() } map
    { (aLines, bLines) => aLines + bLines shouldBe expectedLineCount } recover
    { case _ => 0  } map { result => result shouldBe 0 }
  }