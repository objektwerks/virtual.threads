package objektwerks

import java.util.concurrent.Callable

import scala.io.{Codec, Source}
import scala.util.Using

class FileLineCountTask(file: String) extends Callable[Int]:
  def call(): Int =
    Using(
      Source.fromFile(file, Codec.UTF8.name)
    ) { source =>
      source.getLines().length
    }.get