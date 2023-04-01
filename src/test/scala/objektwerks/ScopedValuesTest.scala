package objektwerks

import jdk.incubator.concurrent.ScopedValue
import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class ScopedValuesTest extends AnyFunSuite with Matchers:
  test("scoped values") {
    val license: ScopedValue[String] = ScopedValue.newInstance()
    val uuid = UUID.randomUUID.toString
    val count = ScopedValue
      .where(license, uuid)
      .call { () => if license.get.nonEmpty then 1 else -1 }
    assert(count == 1)
    assert(!license.isBound)
  }