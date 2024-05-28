package com.fresco;

import com.fresco.util.Pair;
import com.fresco.util.PairInt;
import com.fresco.util.Tuple;

public class MultipleResult {

	public record Person(String name, String address) {
		public Person toUpperCase() {
			return new Person(name.toUpperCase(), address.toUpperCase());
		}

		public Person toLowerCase() {
			return new Person(name.toLowerCase(), address.toLowerCase());
		}
	}

	public Pair<String> swap(String x, String y) {
		return Pair.of(y, x);
	}

	public PairInt swap(int x, int y) {
		return PairInt.of(y, x);
	}

	public Pair<Person> upperCase(Person persona1, Person persona2) {
		return Pair.of(persona1.toUpperCase(), persona2.toUpperCase());
	}

	public Tuple<String, Person> upperCase(String key, Person persona) {
		return Tuple.of(key.toUpperCase(), persona.toUpperCase());
	}

	public MultipleResult() {
		System.out.println();
		var r1 = swap("x", "y");
		System.out.println(r1);
		var r2 = swap(1, 2);
		System.out.println(r2);
		var r3 = upperCase(new Person("Jairo", "Barquisimeto"), new Person("Maria", "Barquisimeto"));
		System.out.println(r3);
		var r4 = upperCase("user1", new Person("Pedro", "Chivacoa"));
		System.out.println(r4);

		var r44 = r4.getValue2().toLowerCase();
		System.out.println(r44);
	}

	public static void main(String[] args) {
		new MultipleResult();
	}
}
