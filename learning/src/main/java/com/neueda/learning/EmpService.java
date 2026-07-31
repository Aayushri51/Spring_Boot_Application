package com.neueda.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpService {
    // inject the depenedency of EmpRepository
    @Autowired
    private EmpRepository empRepository;
    // get all employees
    public List<Employee> getAllEmployees() {
        return empRepository.getAllEmployees();
    }
    // save employee
    public Employee saveEmployee(Employee employee) {
        return empRepository.saveEmployee(employee);
    }
    // get employee by id
    public Employee getEmployeeById(int id){
        return empRepository.getEmployeeById(id);
    }
    // update user by id
    public Employee updateEmpoyee(int id, Employee employee){
        return empRepository.updateEmployee(id, employee);
    }
    // delete employee by id
    public String deleteEmployee(int id){
        return empRepository.deleteEmployee(id);
    }
}
