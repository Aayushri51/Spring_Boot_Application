package com.neueda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
@Repository
public class EmpRepository {
    // core bussiness Logic in repo
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //get All Employees
    public List<Employee> getAllEmployees() {
        String sql="SELECT * FROM employee";
        return jdbcTemplate.query(
                sql,new BeanPropertyRowMapper<>(Employee.class)
        );
    }


    // Save Employee
    public Employee saveEmployee(Employee employee) {
        // prepared Statement to insert employee data into database
        String sql = "INSERT INTO employee (name, department, salary) VALUES (?,?,?)";
        int result = jdbcTemplate.update(
                sql,
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary());
        if (result > 0) {
            return employee;
        }else {
            return null;
        }
    }

    //get Employee by ID
    public Employee getEmployeeById(int id) {

        // RISKY CODE: SQL Injection Vulnerability
        String sql = "SELECT * FROM employee WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql, new BeanPropertyRowMapper<>(Employee.class), id);
        }catch(EmptyResultDataAccessException e){
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
    }

    // update Employee
    public Employee updateEmployee(int id, Employee employee) {

        String sql = "UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?";
        int result = jdbcTemplate.update(
                sql,
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary(),
                id);
        if (result > 0) {
            return employee;
        }else {
            return null;
        }
    }
    // delete Employee
    public int deleteEmployee(int id) {

        String sql = "DELETE FROM employee WHERE id = ?";
        int result = jdbcTemplate.update(sql, id);
        if (result > 0) {
            return 1;
        }else {
            return 0;
        }
    }



}


