package com.fresco;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.minBy;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeDemo2 {

	public record Employee(String name, int age, double salary, List<String> skills, Map<String, String> contactInfo,
			Date hireDate, boolean active, String department) {
	}

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		var objectMapper = new ObjectMapper();

		var aremployees = objectMapper.readValue(EMPLEADOS, Employee[].class);
		// Problem: Get a list of active employees with a salary greater than 50000
		var employees = List.of(aremployees);
		var listEmployee = employees.stream()//
				.filter(Employee::active)//
				.filter(e -> e.salary > 5000)//
				.toList();
		listEmployee.stream()//
				.sorted(comparingDouble(Employee::salary))//
				.forEach(e -> {
					System.out.println("%-20s: %.2f".formatted(e.name, e.salary));
				});
		System.out.println("-".repeat(100));
		// Problem: Find the employee who joined first in each department
		var joinedFirst = employees.stream()//
				.filter(Employee::active)//
				.collect(groupingBy(Employee::department, minBy(comparing(Employee::hireDate))));

		joinedFirst.entrySet().stream()//
				.filter(e -> e.getValue().isPresent())//
				.map(e -> Map.entry(e.getKey(), e.getValue().get()))//
				.forEach(e -> {
					var dpt = e.getKey();
					var emp = e.getValue();
					System.out.println("dpt: %-15s name: %-15s hire: %s".formatted(dpt, emp.name, emp.hireDate));

				});
	}

	public static String EMPLEADOS = """
			[
			  {
			    "name": "John Doe",
			    "age": 30,
			    "salary": 50000,
			    "skills": [
			      "Java",
			      "Python",
			      "SQL"
			    ],
			    "contactInfo": {
			      "Email": "john.doe@example.com",
			      "Phone": "1234567890"
			    },
			    "hireDate": 1585713600000,
			    "department": "BackEnd",
			    "active": true
			  },
			  {
			    "name": "Alice Smith",
			    "age": 34,
			    "salary": 60000,
			    "skills": [
			      "JavaScript",
			      "HTML",
			      "CSS"
			    ],
			    "contactInfo": {
			      "Email": "alice.smith@example.com",
			      "Phone": "9876543210"
			    },
			    "hireDate": 1515729600000,
			    "department": "BackEnd",
			    "active": true
			  },
			  {
			    "name": "Robert Johnson",
			    "age": 28,
			    "salary": 45000,
			    "skills": [
			      "C++",
			      "Python",
			      "Data Analysis"
			    ],
			    "contactInfo": {
			      "Email": "robert.johnson@example.com",
			      "Phone": "4567890123"
			    },
			    "hireDate": 1563163200000,
			    "department": "BackEnd",
			    "active": false
			  },
			  {
			    "name": "Emily Brown",
			    "age": 32,
			    "salary": 55000,
			    "skills": [
			      "Java",
			      "JavaScript",
			      "Angular"
			    ],
			    "contactInfo": {
			      "Email": "emily.brown@example.com",
			      "Phone": "7890123456"
			    },
			    "hireDate": 1488686400000,
			    "department": "BackEnd",
			    "active": true
			  },
			  {
			    "name": "Michael Wilson",
			    "age": 29,
			    "salary": 52000,
			    "skills": [
			      "Python",
			      "Machine Learning",
			      "Big Data"
			    ],
			    "contactInfo": {
			      "Email": "michael.wilson@example.com",
			      "Phone": "2345678901"
			    },
			    "hireDate": 1542686400000,
			    "department": "BackEnd",
			    "active": true
			  },
			  {
			    "name": "Olivia Davis",
			    "age": 31,
			    "salary": 48000,
			    "skills": [
			      "HTML",
			      "CSS",
			      "UI/UX Design"
			    ],
			    "contactInfo": {
			      "Email": "olivia.davis@example.com",
			      "Phone": "6789012345"
			    },
			    "hireDate": 1567396800000,
			    "department": "BackEnd",
			    "active": true
			  },
			  {
			    "name": "James Miller",
			    "age": 33,
			    "salary": 58000,
			    "skills": [
			      "Java",
			      "Spring Framework",
			      "Hibernate"
			    ],
			    "contactInfo": {
			      "Email": "james.miller@example.com",
			      "Phone": "8901234567"
			    },
			    "hireDate": 1592107200000,
			    "department": "FrontEnd",
			    "active": true
			  },
			  {
			    "name": "Sophia Wilson",
			    "age": 27,
			    "salary": 43000,
			    "skills": [
			      "Python",
			      "Data Science",
			      "Data Visualization"
			    ],
			    "contactInfo": {
			      "Email": "sophia.wilson@example.com",
			      "Phone": "4567890123"
			    },
			    "hireDate": 1614052800000,
			    "department": "FrontEnd",
			    "active": true
			  },
			  {
			    "name": "Liam Anderson",
			    "age": 35,
			    "salary": 62000,
			    "skills": [
			      "Java",
			      "Spring Boot",
			      "Microservices"
			    ],
			    "contactInfo": {
			      "Email": "liam.anderson@example.com",
			      "Phone": "6789012345"
			    },
			    "hireDate": 1508212800000,
			    "department": "FrontEnd",
			    "active": false
			  },
			  {
			    "name": "Ava Lee",
			    "age": 28,
			    "salary": 51000,
			    "skills": [
			      "JavaScript",
			      "React",
			      "Node.js"
			    ],
			    "contactInfo": {
			      "Email": "ava.lee@example.com",
			      "Phone": "9012345678"
			    },
			    "hireDate": 1546056000000,
			    "department": "FrontEnd",
			    "active": true
			  },
			  {
			    "name": "Mason Harris",
			    "age": 30,
			    "salary": 54000,
			    "skills": [
			      "Python",
			      "Machine Learning",
			      "Natural Language Processing"
			    ],
			    "contactInfo": {
			      "Email": "mason.harris@example.com",
			      "Phone": "3456789012"
			    },
			    "hireDate": 1565928000000,
			    "department": "FrontEnd",
			    "active": true
			  },
			  {
			    "name": "Isabella Martinez",
			    "age": 29,
			    "salary": 47000,
			    "skills": [
			      "HTML",
			      "CSS",
			      "Bootstrap"
			    ],
			    "contactInfo": {
			      "Email": "isabella.martinez@example.com",
			      "Phone": "7890123456"
			    },
			    "hireDate": 1590379200000,
			    "department": "FrontEnd",
			    "active": true
			  },
			  {
			    "name": "William Wilson",
			    "age": 31,
			    "salary": 60000,
			    "skills": [
			      "Java",
			      "Spring MVC",
			      "RESTful APIs"
			    ],
			    "contactInfo": {
			      "Email": "william.wilson@example.com",
			      "Phone": "1234567890"
			    },
			    "hireDate": 1493784000000,
			    "department": "FullStack",
			    "active": true
			  },
			  {
			    "name": "Charlotte Davis",
			    "age": 32,
			    "salary": 52000,
			    "skills": [
			      "Python",
			      "Data Analysis",
			      "SQL",
			      "C"
			    ],
			    "contactInfo": {
			      "Email": "charlotte.davis@example.com",
			      "Phone": "5678901234"
			    },
			    "hireDate": 1542859200000,
			    "department": "FullStack",
			    "active": false
			  },
			  {
			    "name": "Benjamin Taylor",
			    "age": 34,
			    "salary": 58000,
			    "skills": [
			      "JavaScript",
			      "React Native",
			      "Firebase"
			    ],
			    "contactInfo": {
			      "Email": "benjamin.taylor@example.com",
			      "Phone": "9012345678"
			    },
			    "hireDate": 1562817600000,
			    "department": "FullStack",
			    "active": true
			  },
			  {
			    "name": "Charlotte Taylor",
			    "age": 34,
			    "salary": 68000,
			    "skills": [
			      "JavaScript",
			      "React Native",
			      "Firebase"
			    ],
			    "contactInfo": {
			      "Email": "benjamin.taylor@example.com",
			      "Phone": "9012345678"
			    },
			    "hireDate": 1562817600000,
			    "department": "FullStack",
			    "active": true
			  }
			]
			""";

}
