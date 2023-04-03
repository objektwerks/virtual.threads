package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import ox.Ox.*

import FileLineCountTask.*

class OxTest extends AnyFunSuite with Matchers:
  test("scoped") {
    val lineCount = scoped {
      val alines: Fork[Int] = fork( countLines("./data/data.a.csv") )
      val blines: Fork[Int] = fork( countLines("./data/data.b.csv") )
      alines.join() + blines.join()
    }
    lineCount shouldBe expectedLineCount
  }

  test("nested fork") {
    val lineCount = scoped {
      val alines: Fork[Fork[Int]] = fork {
        val blines: Fork[Int] = fork {
          countLines("./data/data.b.csv")
        }
        countLines("./data/data.a.csv")
        blines
      }
      alines.join().join()
    }
    // expectedLineCount of 540_959 is not received, with alines
    // being counted yet blines not being counted! Why?
    lineCount shouldBe 270_397
  }