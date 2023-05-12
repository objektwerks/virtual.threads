package objektwerks

import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import ox.channels.*
import ox.*

import FileLineCountTask.*

class OxTest extends AnyFunSuite with Matchers:
  test("scoped") {
    val lineCount = scoped {
      val alines: Fork[Int] = fork( countLines("./data/data.a.csv") )
      val blines: Fork[Int] = fork( countLines("./data/data.b.csv") )
      alines.join() + blines.join()
    }
    lineCount shouldBe expectedLineCount
  } // Don't use nested scopes or forks!

  test("scoped value") {
    val license = ForkLocal("")
    val uuid = UUID.randomUUID.toString
    val count = scoped {
      forkHold {
        license.scopedWhere(uuid) {
          if license.get().nonEmpty then 1 else -1
        }
      }
    }.join()
    count shouldBe 1
  }

  /* Refactor to 0.0.6!
  test("channel") {
    scoped {
      val channel = Channel[Int]()
      fork {
        channel.send(1)
        channel.send(2)
        channel.done()
      }

      val unit = fork {
        foreverWhile {
          channel.receive() match
            case Left(error: ChannelState.Error) => println(s"*** channel error: ${error.reason.get}"); false
            case Left(ChannelState.Done)         => println("*** channel done"); false
            case Right(value)                    => println(s"*** channel value: $value"); true
        }
      }
      unit.join()
    }
  }
  */

  test("channel > map") {
    scoped {
      val channel = Channel[Int]()
      fork {
        channel.send(2)
        channel.send(4)
        channel.done()
      }
      channel
        .map(i => i * i)
        .foreach(i => println(s"*** channel map squared $i by ${Math.sqrt(i).toInt}"))
    }
  }

  test("channel > transform") {
    scoped {
      val channel = Channel[Int]()
      fork {
        channel.send(6)
        channel.send(8)
        channel.done()
      }
      channel
        .transform(_.filter(i => i % 2 == 0).map(i => i * i))
        .foreach(i => println(s"*** channel transform squared $i by ${Math.sqrt(i).toInt}"))
    }
  }