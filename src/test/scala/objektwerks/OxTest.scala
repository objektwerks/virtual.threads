package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import ox.Ox.*

import FileLineCountTask.*

class OxTest extends AnyFunSuite with Matchers:
  test("scoped") {
    val count = scoped {
      val alines: Fork[Int] = fork( FileLineCountTask.count("./data/data.a.csv") )
      val blines: Fork[Int] = fork( FileLineCountTask.count("./data/data.b.csv") )
      alines.join() + blines.join()
    }
    count shouldBe expectedLineCount
  }