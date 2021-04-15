package examples3

import scala.runtime.ScalaRunTime

class Person (val firstname:String, val age:Int) extends Product {
  override def productElement(n: Int): Any = {
    if(n == 0){
      firstname
    } else if(n == 1){
      age
    } else {
      throw new IndexOutOfBoundsException("Bugger!")
    }
  }

  override def productIterator: scala.collection.Iterator[scala.Any] = ScalaRunTime.typedProductIterator(Person.this)

  override def equals(obj: scala.Any): Boolean = Person.this.eq(obj.asInstanceOf[AnyRef]) || {
    obj.isInstanceOf[Person]
    }.&&({
   val p: Person = obj.asInstanceOf[Person]
    Person.this.firstname.==(p.firstname).&&(Person.this.age.==(p.age)).&&(p.canEqual(Person.this))
  })


  override def hashCode(): Int = {
    ScalaRunTime._hashCode(this)
  }

  override def productPrefix: String = "Person"

  override def productArity: Int = 2

  override def canEqual(that: Any): Boolean = true


  override def toString: String = {
    ScalaRunTime._toString(this)
  }

  def copy(name:String = this.firstname, age:Int = this.age): Person ={
    new Person(name,age)
  }

}

object Person extends Function2[String, Int,Person] {
  def apply(firstname: String, age: Int):Person = {
    new Person(firstname,age)
  }

  @scala.annotation.unspecialized
  override def tupled: ((String, Int)) => Person = {t: (String, Int) =>
    new Person(t._1, t._2)
  }

  override def toString: String = {
    "Person"
  }

  def unapply(arg: Person): Option[(String, Int)] = Option(arg).map(a => (a.firstname, a.age))
}