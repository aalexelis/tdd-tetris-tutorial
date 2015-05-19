import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(NestedJUnit.class)
public class JavaHelloTest extends Assert {
	
	private final HelloScala scalaHello = new HelloScala();
	private final HelloJava javaHello = new HelloJava();
	
	public class JavaHelloTestScala {
		
		@Test
		public void sais_hello(){
			assertEquals("Hello Scala!", scalaHello.sayHello());
		}
		
	}
	
	public class JavaHelloTestJava {
		
		@Test
		public void sais_hello(){
			assertEquals("Hello Java!", javaHello.sayHello());
		}
		
	}

}
