package springbook.learningtest.jdk.proxy;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

public class DynamicProxyTest {
	
	
	@Test
	public void simpleProxy(){
		//Hello hello = new HelloTarget();
		Hello hello = new Hellouppercase(new HelloTarget());
		assertThat(hello.sayHello("Toby"), is("Hello Toby".toUpperCase()));
		assertThat(hello.sayHi("Toby"), is("Hi Toby".toUpperCase()));
		assertThat(hello.sayThankYou("Toby"), is("Thank you Toby".toUpperCase()));
		
		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[]{Hello.class},
				new UppercaseHandler(new HelloTarget()));
		
		assertThat(proxiedHello.sayHello("Toby"), is("Hello Toby".toUpperCase()));
		assertThat(proxiedHello.sayHi("Toby"), is("Hi Toby".toUpperCase()));
		assertThat(proxiedHello.sayThankYou("Toby"), is("Thank you Toby".toUpperCase()));
		
	}
	
	interface Hello {
		String sayHello(String name);
		String sayHi(String name);
		String sayThankYou(String name);
	}
	
	class HelloTarget implements Hello {

		@Override
		public String sayHello(String name) {
			return "Hello "+name;
		}

		@Override
		public String sayHi(String name) {
			return "Hi "+name;
		}

		@Override
		public String sayThankYou(String name) {
			return "Thank you "+name;
		}		
	}
	
	public class Hellouppercase implements Hello{
		Hello hello;

		public Hellouppercase(Hello hello) {
			this.hello = hello;
		}

		@Override
		public String sayHello(String name) {
			return hello.sayHello(name).toUpperCase();
		}

		@Override
		public String sayHi(String name) {
			return hello.sayHi(name).toUpperCase();
		}

		@Override
		public String sayThankYou(String name) {
			return hello.sayThankYou(name).toUpperCase();
		}
	}
	
	public class UppercaseHandler implements InvocationHandler {
		Object target;
		
		private UppercaseHandler(Object target) {
			this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			Object ret = method.invoke(target, args);
			
			if(ret instanceof String && method.getName().startsWith("say")){
				return ((String) ret).toUpperCase();
			}else {
				return ret;
			}
		}
		
	}
}
