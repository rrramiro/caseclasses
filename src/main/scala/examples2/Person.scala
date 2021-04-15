package examples2

import scala.runtime.ScalaRunTime

class Person(var name: String, var age: Int) extends Product with Serializable {

  override def productElement(n: Int): Any = n match {
    case 0 => name
    case 1 => age
    case _ => throw new IndexOutOfBoundsException(n.toString)
  }

  override def productIterator: scala.collection.Iterator[scala.Any] = ScalaRunTime.typedProductIterator(Person.this)

  override def productArity: Int = 2

  override def productPrefix: String = Person.toString()

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Person]

  override def equals(other: Any): Boolean = this.eq(other.asInstanceOf[AnyRef]) || {
    other.isInstanceOf[Person]
    }.&&({
      val p: Person = other.asInstanceOf[Person]
      Person.this.name.==(p.name).&&(Person.this.age.==(p.age)).&&(p.canEqual(Person.this))
    })
  override def hashCode: Int = ScalaRunTime._hashCode(this)

  override def toString: String = ScalaRunTime._toString(this)

  def copy(name: String = this.name, age: Int = this.age) = new Person(name, age)

}

object Person extends ((String, Int) => Person) with Serializable {
  override def toString(): String = "Person"

  def apply(name: String, age: Int) = new Person(name, age)

  def unapply(arg: Person): Option[(String, Int)] = Option(arg).map { other => other.name -> other.age }

}
