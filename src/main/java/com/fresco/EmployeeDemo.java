package com.fresco;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.summingDouble;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fresco.domain.Employee;

public class EmployeeDemo {
	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<>();

		// Create date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		try {
			// Create Employee objects
			Employee emp1 = new Employee("John Doe", 30, 50000.0, Arrays.asList("Java", "Python", "SQL"),
					getContactInfo("john.doe@example.com", "1234567890"), dateFormat.parse("01/04/2020"), true,
					"BackEnd");
			employees.add(emp1);

			Employee emp2 = new Employee("Alice Smith", 34, 60000.0, Arrays.asList("JavaScript", "HTML", "CSS"),
					getContactInfo("alice.smith@example.com", "9876543210"), dateFormat.parse("12/01/2018"), true,
					"BackEnd");
			employees.add(emp2);

			Employee emp3 = new Employee("Robert Johnson", 28, 45000.0, Arrays.asList("C++", "Python", "Data Analysis"),
					getContactInfo("robert.johnson@example.com", "4567890123"), dateFormat.parse("15/07/2019"), false,
					"BackEnd");
			employees.add(emp3);

			Employee emp4 = new Employee("Emily Brown", 32, 55000.0, Arrays.asList("Java", "JavaScript", "Angular"),
					getContactInfo("emily.brown@example.com", "7890123456"), dateFormat.parse("05/03/2017"), true,
					"BackEnd");
			employees.add(emp4);

			Employee emp5 = new Employee("Michael Wilson", 29, 52000.0,
					Arrays.asList("Python", "Machine Learning", "Big Data"),
					getContactInfo("michael.wilson@example.com", "2345678901"), dateFormat.parse("20/11/2018"), true,
					"BackEnd");
			employees.add(emp5);

			Employee emp6 = new Employee("Olivia Davis", 31, 48000.0, Arrays.asList("HTML", "CSS", "UI/UX Design"),
					getContactInfo("olivia.davis@example.com", "6789012345"), dateFormat.parse("02/09/2019"), true,
					"BackEnd");
			employees.add(emp6);

			Employee emp7 = new Employee("James Miller", 33, 58000.0,
					Arrays.asList("Java", "Spring Framework", "Hibernate"),
					getContactInfo("james.miller@example.com", "8901234567"), dateFormat.parse("14/06/2020"), true,
					"FrontEnd");
			employees.add(emp7);

			Employee emp8 = new Employee("Sophia Wilson", 27, 43000.0,
					Arrays.asList("Python", "Data Science", "Data Visualization"),
					getContactInfo("sophia.wilson@example.com", "4567890123"), dateFormat.parse("23/02/2021"), true,
					"FrontEnd");
			employees.add(emp8);

			Employee emp9 = new Employee("Liam Anderson", 35, 62000.0,
					Arrays.asList("Java", "Spring Boot", "Microservices"),
					getContactInfo("liam.anderson@example.com", "6789012345"), dateFormat.parse("17/10/2017"), false,
					"FrontEnd");
			employees.add(emp9);

			Employee emp10 = new Employee("Ava Lee", 28, 51000.0, Arrays.asList("JavaScript", "React", "Node.js"),
					getContactInfo("ava.lee@example.com", "9012345678"), dateFormat.parse("29/12/2018"), true,
					"FrontEnd");
			employees.add(emp10);

			Employee emp11 = new Employee("Mason Harris", 30, 54000.0,
					Arrays.asList("Python", "Machine Learning", "Natural Language Processing"),
					getContactInfo("mason.harris@example.com", "3456789012"), dateFormat.parse("16/08/2019"), true,
					"FrontEnd");
			employees.add(emp11);

			Employee emp12 = new Employee("Isabella Martinez", 29, 47000.0, Arrays.asList("HTML", "CSS", "Bootstrap"),
					getContactInfo("isabella.martinez@example.com", "7890123456"), dateFormat.parse("25/05/2020"), true,
					"FrontEnd");
			employees.add(emp12);

			Employee emp13 = new Employee("William Wilson", 31, 60000.0,
					Arrays.asList("Java", "Spring MVC", "RESTful APIs"),
					getContactInfo("william.wilson@example.com", "1234567890"), dateFormat.parse("03/05/2017"), true,
					"FullStack");
			employees.add(emp13);

			Employee emp14 = new Employee("Charlotte Davis", 32, 52000.0,
					Arrays.asList("Python", "Data Analysis", "SQL", "C"),
					getContactInfo("charlotte.davis@example.com", "5678901234"), dateFormat.parse("22/11/2018"), false,
					"FullStack");
			employees.add(emp14);

			Employee emp15 = new Employee("Benjamin Taylor", 34, 58000.0,
					Arrays.asList("JavaScript", "React Native", "Firebase"),
					getContactInfo("benjamin.taylor@example.com", "9012345678"), dateFormat.parse("11/07/2019"), true,
					"FullStack");
			employees.add(emp15);

			Employee emp16 = new Employee("Charlotte Taylor", 34, 68000.0,
					Arrays.asList("JavaScript", "React Native", "Firebase"),
					getContactInfo("benjamin.taylor@example.com", "9012345678"), dateFormat.parse("11/07/2019"), true,
					"FullStack");
			employees.add(emp16);
			record Empleados (List<Employee> employees) {}
			var empleados = new Empleados(employees);
			var objectMapper = new ObjectMapper();
			var json = objectMapper.writeValueAsString(employees);
			System.out.println(json);
			// Display employee details
			//resolve(employees);
			//resolveComplex(employees);
		} catch (ParseException | JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	// Helper method to create contact info map
	private static Map<String, String> getContactInfo(String email, String phone) {
		Map<String, String> contactInfo = new HashMap<>();
		contactInfo.put("Email", email);
		contactInfo.put("Phone", phone);
		return contactInfo;
	}

	/*
	 * 
	 */
	private static void resolve(List<Employee> employees) {
		// Problem: Get a list of active employees with a salary greater than 50000
		var listEmployee = employees.stream()//
				.filter(e -> e.isActive())//
				.filter(e -> e.getSalary() > 5000)//
				.toList();
		listEmployee.stream()//
				.forEach(System.out::println);
		System.out.println("-".repeat(500));
		// Problem: Get the total number of employees who possess Java and Python skills
		var count = employees.stream()//
				.filter(e -> e.getSkills().contains("Java"))//
				.filter(e -> e.getSkills().contains("Python"))//
				.count();
		System.out.println(count);
		System.out.println("-".repeat(500));
		// Problem: Find the employee with the highest salary
		var maxSalary = employees.stream()//
				.max(comparingDouble(e -> e.getSalary()));
		if (maxSalary.isPresent()) {
			System.out.println(maxSalary.get());
		} else {
			System.out.println("No hay");
		}
		System.out.println("-".repeat(500));
		// Problem: Group employees by their age
		var employeesByAge = employees.stream()//
				.collect(groupingBy(Employee::getAge));
		employeesByAge.forEach((edad, empleados) -> {
			System.out.println(edad + " " + empleados);
		});
		System.out.println("-".repeat(500));
		// Problem: Calculate the average salary of all employees
		var avgSalary = employees.stream()//
				.mapToDouble(Employee::getSalary)//
				.average().orElse(0.0);
		System.out.println(avgSalary);
		System.out.println("-".repeat(500));
	}

	/*
	 * 
	 */
	private static void resolveComplex(List<Employee> employees) {
		// Problem: Find the employee with the most number of skills
		var maxSkill = employees.stream()//
				.max(comparingInt(e -> e.getSkills().size()));
		System.out.println(maxSkill);
		System.out.println("-".repeat(500));
		// Problem: Calculate the total salary of active employees in each department
		var totalSalary = employees.stream()//
				.filter(Employee::isActive)//
				.collect(groupingBy(Employee::getDepartment, summingDouble(Employee::getSalary)));
		totalSalary.forEach((departamento, stadistica) -> {
			System.out.println(departamento + " " + stadistica);
		});
		System.out.println("-".repeat(500));
		// Problem: Find the employee who joined first in each department
		var joinedFirst = employees.stream()//
				.filter(Employee::isActive)//
				.collect(groupingBy(Employee::getDepartment,
						minBy(comparing(Employee::getHireDate))));

		joinedFirst.forEach((departamento, employee) -> {
			System.out.println(departamento + " " + employee);
		});
		System.out.println("-".repeat(500));
		// Problem: Check if any employee has both a skill and a contact detail
		// containing the keyword "example"

		var keywordExample = employees.stream()//
				.filter(e -> e.getSkills().size() > 0)
				.filter(e -> e.getContactInfo().values().stream().anyMatch(s -> s.contains("example")))//
				.map(e -> true)//
				.findAny().orElse(false);
		System.out.println(keywordExample);
		System.out.println("-".repeat(500));

		// Problem: Sort employees based on their skills (lexicographically) and then by
		// salary (descending)
		var sortEmployees = employees.stream()//
				.sorted(comparing(Employee::getSkills, comparing(List::toString))
						.thenComparingDouble(Employee::getSalary).reversed());

		sortEmployees.forEach(System.out::println);
		System.out.println("-".repeat(500));
	}
}
