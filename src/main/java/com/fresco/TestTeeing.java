package com.fresco;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TestTeeing {

	public record Employee(String name, int age, String department) {
	}

	public static List<Employee> employees = List.of(
			new Employee("Robert Smith", 35, "Engineering"),
			new Employee("Jennifer Lee", 29, "Marketing"),
			new Employee("David Wilson", 42, "Sales"),
			new Employee("Linda Johnson", 27, "Human Resources"),
			new Employee("Christopher Garcia", 38, "Finance"),
			new Employee("Barbara Rodriguez", 35, "Operations"),
			new Employee("Michael Williams", 31, "Technology"),
			new Employee("Susan Brown", 29, "Marketing"),
			new Employee("James Davis", 48, "Product Management"),
			new Employee("Patricia Miller", 33, "Research & Development"),
			new Employee("John Jones", 35, "Engineering"),
			new Employee("Mary Green", 28, "Marketing"),
			new Employee("Charles White", 39, "Sales"),
			new Employee("Margaret Black", 30, "Human Resources"),
			new Employee("Joseph King", 42, "Sales"));

	public static void main(String[] args) {
		record GroupsEmployee(Map<Integer, Long> group1, Map<String, Long> group2) {
		}

		var groupAge = Collectors.groupingBy(Employee::age, TreeMap::new, Collectors.counting());
		var groupDepartament = Collectors.groupingBy(Employee::department, Collectors.counting());
		var groups = employees.stream()
				.collect(Collectors.teeing(groupAge, groupDepartament, (r1, r2) -> new GroupsEmployee(r1, r2)));

		System.out.println("-".repeat(50));
		System.out.println("Group by age");
		System.out.println("-".repeat(50));
		groups.group1.forEach((y, c) -> System.out.println("%d: %d".formatted(y, c)));
		System.out.println("-".repeat(50));
		System.out.println("Group by department");
		System.out.println("-".repeat(50));
		groups.group2.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEach(e -> System.out.println("%-25s: %d".formatted(e.getKey(), e.getValue())));
	}
}
