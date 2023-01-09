package objektwerks

import java.util.concurrent.Executors

import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import FileLineCountTask.*

final class VirtualFutureTest extends AsyncFunSuite with Matchers:
  implicit override val executionContext: ExecutionContext = ExecutionContext.fromExecutor( Executors.newVirtualThreadPerTaskExecutor() )

  test("zip") {
    Future { count("./data/data.a.csv") } zip
    Future { count("./data/data.b.csv") } map
    { (aLines, bLines) => aLines + bLines shouldBe expectedLineCount }
  }

  test("sequence") {
    for
      aLines <- Future { count("./data/data.a.csv") }
      bLines <- Future { count("./data/data.b.csv") }
    yield aLines + bLines shouldBe expectedLineCount
  }

  test("parallel") {
    val aFuture = Future { count("./data/data.a.csv") }
    val bFuture = Future { count("./data/data.b.csv") }
    for
      aLines <- aFuture
      bLines <- bFuture
    yield aLines + bLines shouldBe expectedLineCount
  }

  test("recover") {
    Future { count("./data/data.a.csv") } zip
    Future { count("./data/wrong.name.csv") } map
    { (aLines, bLines) => aLines + bLines shouldBe expectedLineCount } recover
    { case _ => -1  } map { result => result shouldBe -1 }
  }

  test("recover > for") {
    val result = (
      for
        aLines <- Future { count("./data/data.a.csv") }
        bLines <- Future { count("./data/wrong.name.csv") }
      yield aLines + bLines shouldBe expectedLineCount
    ).recover { case _ => -1 }
    result map { result => result shouldBe -1 }
  }