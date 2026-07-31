package com.neueda.rest;
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
    public int saveEmployee(Employee employee) {
        return empRepository.saveEmployee(employee) !=null ? 1 : 0;
    }

    // get employee by id
    public Employee getEmployeeById(int id){
        Employee employee = empRepository.getEmployeeById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        return employee;
    }
    // update user by id
    public Employee updateEmpoyee(int id, Employee employee){
        return empRepository.updateEmployee(id, employee);
    }
    // delete employee by id
    public int deleteEmployee(int id){
        return empRepository.deleteEmployee(id);
    }


}
