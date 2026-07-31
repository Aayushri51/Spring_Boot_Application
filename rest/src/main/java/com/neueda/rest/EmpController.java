package com.neueda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmpController {
    @Autowired
    EmpService empService;
    // Get all employees
    @GetMapping("/") // localhost:8081/api/v1/employees/
    public ResponseEntity<Map<String,Object>> getAllEmployees() {
        List<Employee> employees= empService.getAllEmployees();
        Map<String,Object> response= new HashMap<>();
        response.put("message","Employees fetched successfully");
        response.put("data",employees);
        return ResponseEntity.status(200).body(response);
    }
    // Save employee
    @PostMapping("/") // localhost:8081/api/v1/employees/
    public ResponseEntity<Map<String,Object>> addEmployee(@RequestBody Employee employee) {
        int result= empService.saveEmployee(employee);
        Map<String,Object> response= new HashMap<>();
        if(result>0){
            response.put("message","Employee added successfully");
            response.put("data",employee);
            return ResponseEntity.status(201).body(response);
        }else{
            response.put("message","Employee not added");
            return ResponseEntity.status(400).body(response);
        }
        //return ;
        //api must give response in json format

    }

    // get employee by id
    @GetMapping("/{id}") // localhost:8081/api/v1/employees/1
    public ResponseEntity<Map<String,Object>>getEmployee(@PathVariable int id){
        Employee employee= empService.getEmployeeById(id);
        Map<String,Object> response= new HashMap<>();
        if(employee!=null){
            response.put("message","Employee fetched successfully");
            response.put("data",employee);
            return ResponseEntity.status(200).body(response);
        }else{
            response.put("message","Employee not found");
            return ResponseEntity.status(404).body(response);
        }
        //return  empService.getEmployeeById(id);
    }

    // put mapping to update employee
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>>updateEmployee(@PathVariable int id,@RequestBody Employee employee) {
        int result= empService.saveEmployee(employee);
        Map<String,Object> response= new HashMap<>();
        if(result>0){
            response.put("message","Employee updated successfully");
            response.put("data",employee);
            return ResponseEntity.status(200).body(response);
        }else{
            response.put("message","Employee not updated");
            return ResponseEntity.status(400).body(response);
        }
        //return empService.updateEmpoyee(id, employee);
    }
    // delete employee by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteEmployee(@PathVariable int id){
        int result= empService.deleteEmployee(id);
        Map<String,Object> response= new HashMap<>();
        if(result>0){
            response.put("message","Employee deleted successfully");
            return ResponseEntity.status(200).body(response);
        }else{
            response.put("message","Employee not deleted");
            return ResponseEntity.status(400).body(response);
        }
        //return empService.deleteEmployee(id);
    }


}
