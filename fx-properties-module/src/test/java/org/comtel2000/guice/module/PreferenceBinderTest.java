package org.comtel2000.guice.module;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PreferenceBinderTest {

	static PreferenceBinder context;

	@BeforeClass
	public static void init() {
		context = new PreferenceBinder(PreferenceBinderTest.class);
	}

	@AfterClass
	public static void stop() throws BackingStoreException {
		context.getPreferences().removeNode();
	}

	@After
	public void cleanup() throws BackingStoreException {
		context.getPreferences().clear();
		context.flush();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKey() {
		context.bind(new SimpleStringProperty("init"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testKeyLength() {
		StringBuilder sb = new StringBuilder();
		IntStream.range(0, 46).forEach(sb::append);
		context.bind(new SimpleStringProperty("init"), sb.toString());
	}

	@Test
	public void testStringProperty() {
		StringProperty myString = new SimpleStringProperty("init");
		context.bind(myString, "myString");
		myString.set("updated");

		StringProperty myString2 = new SimpleStringProperty("init2");
		context.bind(myString2, "myString");
		assertEquals("updated", myString2.get());
		myString2.set("updated2");

		StringProperty myString3 = new SimpleStringProperty("init3");
		context.bind(myString3, "myString");
		assertEquals("updated2", myString2.get());

	}

	@Test
	public void testStringPropertyName() {
		StringProperty myString = new SimpleStringProperty("init", "myPropertyName");
		context.bind(myString);
		myString.set("updated");

		StringProperty myString2 = new SimpleStringProperty("init2", "myPropertyName");
		context.bind(myString2);
		assertEquals("updated", myString2.get());

		myString2.set("updated2");
	}

	@Test
	public void testDoubleProperty() {
		DoubleProperty prop1 = new SimpleDoubleProperty(12.45);
		context.bind(prop1, "testDoubleProperty1");
		prop1.set(Double.MAX_VALUE);

		DoubleProperty prop2 = new SimpleDoubleProperty(12.45);
		context.bind(prop2, "testDoubleProperty1");
		assertEquals(Double.MAX_VALUE, prop2.get(), 0.1);

		prop2.set(Double.MIN_VALUE);
	}

	@Test
	public void testFloatProperty() {
		FloatProperty prop1 = new SimpleFloatProperty(12.45f);
		context.bind(prop1, "testFloatProperty1");
		prop1.set(Float.MAX_VALUE);

		FloatProperty prop2 = new SimpleFloatProperty(12.45f);
		context.bind(prop2, "testFloatProperty1");
		assertEquals(Float.MAX_VALUE, prop2.get(), 0.1);

		prop2.set(Float.MIN_VALUE);
	}

	@Test
	public void testIntProperty() {
		IntegerProperty prop1 = new SimpleIntegerProperty(1245);
		context.bind(prop1, "testIntProperty1");
		prop1.set(Integer.MAX_VALUE);

		IntegerProperty prop2 = new SimpleIntegerProperty(1245);
		context.bind(prop2, "testIntProperty1");
		assertEquals(Integer.MAX_VALUE, prop2.get());

		prop2.set(Integer.MIN_VALUE);
	}

	@Test
	public void testLongProperty() {
		LongProperty prop1 = new SimpleLongProperty(1245);
		context.bind(prop1, "testLongProperty1");
		prop1.set(Long.MAX_VALUE);

		LongProperty prop2 = new SimpleLongProperty(1245);
		context.bind(prop2, "testLongProperty1");
		assertEquals(Long.MAX_VALUE, prop2.get());

		prop2.set(Long.MIN_VALUE);
	}

	@Test
	public void testObjectProperty() {
		ObjectProperty<Person> prop1 = new SimpleObjectProperty<>(new Person("demo", 99));
		context.bind(prop1, "testObjectProperty1");
		prop1.set(new Person("demo34", 99));

		ObjectProperty<Person> prop2 = new SimpleObjectProperty<>(new Person("demo", 199));
		context.bind(prop2, "testObjectProperty1");
		assertEquals(new Person("demo34", 99), prop2.get());
	}

	@Test
	public void testObjectPropertyName() {
		ObjectProperty<Person> prop1 = new SimpleObjectProperty<>(new Person("demo", 99), "testObjectPropertyName1");
		context.bind(prop1);
		prop1.set(new Person("demo34", 99));

		ObjectProperty<Person> prop2 = new SimpleObjectProperty<>(new Person("demo", 199), "testObjectPropertyName1");
		context.bind(prop2);
		assertEquals(new Person("demo34", 99), prop2.get());
	}

	@Test
	public void testObjectPropertyPersist() throws BackingStoreException {

		PreferenceBinder internal = new PreferenceBinder(Preferences.userRoot().node("sample.unittest"));

		ObjectProperty<Person> prop1 = new SimpleObjectProperty<>(new Person("demo", 99));
		internal.bind(prop1, "internalObjectProperty1");
		prop1.set(new Person("demo34", 99));
		internal.flush();

		PreferenceBinder internal2 = new PreferenceBinder(Preferences.userRoot().node("sample.unittest"));

		ObjectProperty<Person> prop2 = new SimpleObjectProperty<>(new Person("demo", 199));
		internal2.bind(prop2, "internalObjectProperty1");
		assertEquals(new Person("demo34", 99), prop2.get());
		internal2.getPreferences().removeNode();
	}

	static class Person implements Serializable {
		private static final long serialVersionUID = -7715330739946079293L;
		private String name;
		private int age;

		public Person() {

		}

		public Person(String name, int age) {
			setName(name);
			setAge(age);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (age != other.age)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}
}