package examples1

import org.scalatest.funsuite.AnyFunSuite

class SpecialCaseClassTest extends AnyFunSuite{
  test("match medium") {
    val mediumCaseClass = MediumCaseClass.apply()
    mediumCaseClass match {
      case MediumCaseClass(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v) =>
      case _ => fail("should match")
    }
  }

  test("match big"){
    val bigCaseClass = BigCaseClass.apply()
    bigCaseClass match {
      case BigCaseClass(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, aa, ab, ac, ad, ae) =>
      case _ =>
        fail("should match")
    }
  }
}
