package com.neueda.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // localhost:8080/
@RequestMapping("/api/v1/employees") // localhost:8080/api/v1/employees
public class EmpContrller {
    @Autowired
    EmpService empService;
    // Get all employees
    @GetMapping("/") // localhost:8080/api/v1/employees/
    public List<Employee> getAllEmployees() {
        return empService.getAllEmployees();
    }
    // get employee by id
    @GetMapping("/{id}") // localhost:8080/api/v1/employees/1
    public Employee getEmployee(@PathVariable int id){
        return  empService.getEmployeeById(id);
    }
    // Save employee
    @PostMapping("/") // localhost:8080/api/v1/employees/
    public Employee addEmployee(@RequestBody Employee employee) {
        return empService.saveEmployee(employee);
    }
    // put mapping to update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id,@RequestBody Employee employee) {
        return empService.updateEmpoyee(id, employee);
    }
    // delete employee by id
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id){
        return empService.deleteEmployee(id);
    }
}
