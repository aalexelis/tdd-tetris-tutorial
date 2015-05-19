import org.scalatest.Assertions
import org.junit.Test

/**
 * @author andreas
 */
class ScalaHelloTest extends Assertions {
  
  @Test def scalaHelloTestScala() {
    val scalaHello = new HelloScala();
    assert(scalaHello.sayHello === "Hello Scala!")
  }

  @Test def scalaHelloTestJava() {
    val javaHello = new HelloJava();
    assert(javaHello.sayHello() === "Hello Java!")
  }
  
}