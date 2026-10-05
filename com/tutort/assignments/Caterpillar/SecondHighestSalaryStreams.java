package com.tutort.assignments.Caterpillar;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class Employee {
    private String name;
    private String department;
    private int salary;

    public Employee(String name, String department, int salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}

public class SecondHighestSalaryStreams {
    public static void main(String[] args) {
        List<Employee> employees = List.of(new Employee("A", "IT", 120000),
                new Employee("B", "HR", 90000),
                new Employee("C", "Engineering", 150000),
                new Employee("D", "IT", 125000),
                new Employee("E", "HR", 40000),
                new Employee("F", "Engineering", 175000),
                new Employee("G", "IT", 50000));
        Map<String, Optional<Employee>> result = employees.stream().collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> list.stream()
                                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                                .skip(1)
                                .findFirst()
                )
        ));
        System.out.println(result);
    }
}
