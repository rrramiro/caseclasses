package examples1

import org.scalatest.funsuite.AnyFunSuite

import scala.runtime.ScalaRunTime

class CaseClassPersonTest extends AnyFunSuite {
  private def _equals(x: Product, y: Any): Boolean = y match {
    case y1: Product if x.productArity == y1.productArity =>
      val arity = x.productArity
      var i = 0
      while (i < arity && x.productElement(i) == y1.productElement(i))
        i += 1
      i == arity
    case _ =>
      false
  }

  test("apply & equals") {
    val pA1 = Person("Alex1", 12)
    val pA2 = Person.apply("Alex", 12)
    val pA3 = Person("Alex", 12)
    assert(pA1 === pA1)
    assert(pA1 !== pA2)
    assert(pA3 === pA2)
    assert(_equals(pA1, pA2) === false)
    assert(_equals(pA3, pA2))
  }

  test("hashCode") {
    val p1 = Person("Alex", 12)
    val p2 = Person("Alex1", 12)
    val p3 = Person("Alex1", 12)
    assert(p2.hashCode === p3.hashCode)
    val personSet = Set(p1, p2, p3)
    assert(personSet.size === 2)
    assert(ScalaRunTime._hashCode(p2) === ScalaRunTime._hashCode(p3))
  }

  test("copy") {
    val pA0 = Person("Alex1", 12).copy(name = "Alex")
    val pA1 = pA0.copy(age = 11)
    val pA2 = Person("Alex", 11)
    assert(pA1 === pA2)
  }

  test("toString") {
    val pA1 = Person("Alex1", 12)
    assert(pA1.toString === "Person(Alex1,12)")
    assert(Person.toString === "Person")
    assert(ScalaRunTime._toString(pA1) === "Person(Alex1,12)")
  }

  test("unapply") {
    val pA1 = Person("Alex1", 12)
    pA1 match {
      case Person(name, age) =>
        assert(name === "Alex1")
        assert(age === 12)
    }
    null.asInstanceOf[Person] match {
      case Person(name, age) =>
        fail("should fail")
      case _ =>
    }
  }

  test("product") {
    val pA1 = Person("Alex1", 12)
    assert("Person" === pA1.productPrefix)
    assert(2 === pA1.productArity)
    assert("Alex1" === pA1.productElement(0))
    assert(12 === pA1.productElement(1))
    assert(pA1.productIterator.toSeq === Seq("Alex1", 12))

    intercept[IndexOutOfBoundsException] {
      pA1.productElement(2)
    }
  }

  test("tupled") {
    val pA1 = Person("Alex1", 12)
    val tuple: (String, Int) = ("Alex1", 12)
    assert(Person.tupled(tuple) === pA1)
  }

  test("curried") {
    val pA1 = Person("Alex1", 12)
    val fun1: (Int) => Person = Person.curried("Alex1")
    assert(fun1(12) === pA1)
  }

}
