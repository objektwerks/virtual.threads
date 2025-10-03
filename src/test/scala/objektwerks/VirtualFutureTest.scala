package objektwerks

import java.util.concurrent.Executors

import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

final class VirtualFutureTest extends AsyncFunSuite with Matchers:
  given ExecutionContext = ExecutionContext.fromExecutor( Executors.newVirtualThreadPerTaskExecutor() )

  test("zip"):
    Future { FileLineCountTask.countLines("./data/data.a.csv") } zip
    Future { FileLineCountTask.countLines("./data/data.b.csv") } map
    { (aLines, bLines) => aLines + bLines shouldBe FileLineCountTask.expectedLineCount }

  test("sequence"):
    for
      aLines <- Future { FileLineCountTask.countLines("./data/data.a.csv") }
      bLines <- Future { FileLineCountTask.countLines("./data/data.b.csv") }
    yield aLines + bLines shouldBe FileLineCountTask.expectedLineCount

  test("parallel"):
    val aFuture = Future { FileLineCountTask.countLines("./data/data.a.csv") }
    val bFuture = Future { FileLineCountTask.countLines("./data/data.b.csv") }
    for
      aLines <- aFuture
      bLines <- bFuture
    yield aLines + bLines shouldBe FileLineCountTask.expectedLineCount

  test("recover"):
    Future { FileLineCountTask.countLines("./data/data.a.csv") } zip
    Future { FileLineCountTask.countLines("./data/wrong.name.csv") } map
    { (aLines, bLines) => aLines + bLines shouldBe FileLineCountTask.expectedLineCount } recover
    { case _ => -1  } map { result => result shouldBe -1 }

  test("recover > for"):
    val result = (
      for
        aLines <- Future { FileLineCountTask.countLines("./data/data.a.csv") }
        bLines <- Future { FileLineCountTask.countLines("./data/wrong.name.csv") }
      yield aLines + bLines shouldBe FileLineCountTask.expectedLineCount
    ).recover { case _ => -1 }
    result map { result => result shouldBe -1 }