package objektwerks

import java.util.concurrent.Callable

import scala.io.{Codec, Source}
import scala.util.Using

object FileLineCountTask:
  val tasks = List( FileLineCountTask("./data/data.a.csv"), FileLineCountTask("./data/data.b.csv") )
  val expectedLineCount = 540_959

  def count(file: String): Int =
    Using(
      Source.fromFile(file, Codec.UTF8.name)
    ) { source =>
      source.getLines.length
    }.get

final class FileLineCountTask(file: String) extends Callable[Int]:
  def call(): Int = FileLineCountTask.count(file)