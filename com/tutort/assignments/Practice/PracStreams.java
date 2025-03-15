package com.tutort.assignments.Practice;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Employee {
    private int id;
    private String empName;
    private int marks;

    public Employee(int id, String empName, int marks) {
        this.id = id;
        this.empName = empName;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", marks=" + marks +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}

public class PracStreams {
    // java streams practice

    public static void main(String[] args) {

//		forEach
//		forEach() is simplest and most common operation; it loops over the stream elements, 
//		calling the supplied function on each element.

        // create a list of integers
        List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);

        // demonstration of map method
        List<Integer> square = number.stream().map(x -> x * x).collect(Collectors.toList());
        System.out.println(square); // 1 4 9 16 25

        // create a list of String
        List<String> names = Arrays.asList("Reflection", "Collection", "Stream", "Java", "India", "Africa", "CGI",
                "Bell", "Canada", "Andhra Pradesh", "A", "a");

        // demonstration of filter method
        List<String> result = names.stream().filter(s -> s.startsWith("S")).collect(Collectors.toList());
        System.out.println(result);

        // Creating a list of strings starting from "A" USING filter
        List<String> res = names.stream().filter(s -> s.startsWith("A")).collect(Collectors.toList());
        System.out.println(res); // so, it's case sensitive

        // demonstration of sorted method
        List<String> show = names.stream().sorted().collect(Collectors.toList());
        // Based on ASCII
        // 65 A 97 a
        System.out.println(show); // [A, Africa, Andhra Pradesh, Bell, CGI, Canada, Collection, India, Java,
        // Reflection, Stream, a]

        // create a list of integers
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 2, 4, 6, 8, 10, 11, 12);

        // collect method returns a set
        Set<Integer> squareSet = numbers.stream().map(x -> x * x).collect(Collectors.toSet());
        // set make sure that no duplicate elements are present
        System.out.println(squareSet);

        // demonstration of forEach method
        number.stream().map(x -> x * x).forEach(y -> System.out.print(y + " : "));
        System.out.println();
        // demonstration of reduce method
        int even = numbers.stream().filter(x -> x % 2 == 0).reduce(0, Integer::sum);
        // sum of all even numbers present in numbers
        System.out.println(even);
        // Given a list of integers, find the maximum value element present in it using Stream functions?
        List<Integer> list = Arrays.asList(10, 15, 8, 49, 25, 99, 98, 32, 15);
        Integer ans = list.stream().max(Integer::compare).get();
        System.out.println(ans);
        System.out.println("---------------Very Important-------------------");
        List<Employee> empList = Arrays.asList(new Employee(1, "CGI", 100), new Employee(2, "CGI India", 200), new Employee(3, "CGI", 300), new Employee(4, "CGI France", 400));
        // to print Employee details
        empList.stream().filter(empName -> empName.getEmpName() == "CGI").forEach(System.out::println);
        // to print marks
        empList.stream().filter(empName -> empName.getEmpName() == "CGI").map(Employee::getMarks).forEach(System.out::println);
        // to print empName
        empList.stream().filter(empName -> empName.getEmpName() == "CGI").map(Employee::getEmpName).forEach(System.out::println);
    }
}
