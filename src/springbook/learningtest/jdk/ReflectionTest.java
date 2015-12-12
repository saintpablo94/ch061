package springbook.learningtest.jdk;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionTest {
	@Test
	public void invokeMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name = "Spring";
		
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat(lengthMethod.invoke(name), is(6));
		
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat(charAtMethod.invoke(name,0), is('S'));
	}
}
