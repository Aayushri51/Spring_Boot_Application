package com.neueda.learning;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class EmpRepository {
    // core bussiness Logic in repo
    private List<Employee> employees = new ArrayList<>();

    public EmpRepository() {
        employees.add(new Employee(1, "John Doe", "Software Engineer", 25000.00));
        employees.add(new Employee(2, "Jane Smith", "Project Manager", 56000.00));
        employees.add(new Employee(3, "Mike Johnson", "QA Engineer", 80000.00));
    }
    //get All Employees
    public List<Employee> getAllEmployees() {
        return employees;
    }
    // Save Employee
    public Employee saveEmployee(Employee employee) {
        employees.add(employee); // add the data to the list
        return employee;
    }
    //get Employee by ID
    public Employee getEmployeeById(int id) {
        for(Employee emp:employees){
            if(emp.getId()==id) {
                return emp;
            }
        }
        return null;
    }
    // update Employee
    public Employee updateEmployee(int id, Employee employee) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                emp.setName(employee.getName());
                emp.setDepartment(employee.getDepartment());
                emp.setSalary(employee.getSalary());
                return emp;
            }
        }
        return null;
    }
    // delete Employee
    public String deleteEmployee(int id) {
        employees.removeIf(emp-> emp.getId()==id);
        return "Employee with deleted successfully";
    }

}


