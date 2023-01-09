package objektwerks

import java.util.concurrent.Executors

import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.ExecutionContext

final class VirtualFutureTest extends AnyFunSuite:
  implicit val executionContext: ExecutionContext = ExecutionContext.fromExecutor( Executors.newVirtualThreadPerTaskExecutor() )

  test("future") {

  }