package com.neueda.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@SpringBootTest

public class EmployeeRepoTest {
    @Autowired
    EmpRepository repo;
    @Test
    void shouldGetAllEmployees() {
        List<Employee> employees= repo.getAllEmployees();
        assert(employees.size()>0);
        assertFalse(employees.isEmpty());
    }
    @Test
    void shouldGetEmployeeById() {
        Employee employee = repo.getEmployeeById(1);
        assert (employee != null);
    }
    @Test
    void shouldSaveEmployee() {
        Employee employee = new Employee("Test User", "IT", 50000);
        Employee savedEmployee = repo.saveEmployee(employee);
        assert (savedEmployee != null);
    }
    @Test
    void shouldUpdateEmployee() {
        Employee employee = new Employee("Test User", "IT", 50000);
        Employee savedEmployee = repo.saveEmployee(employee);
        savedEmployee.setName("Updated User");
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getName()).isEqualTo("Updated User");
    }
    

    @Test
    void shouldThrowExceptionForNonExistentEmployeeId() {
        assertThatThrownBy(() -> repo.getEmployeeById(99999))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("Employee with ID 99999 not found.");
    }

    @Test
    void shouldReturnZeroWhenDeletingNonExistentEmployee() {
        int result = repo.deleteEmployee(99999);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistentEmployee() {
        Employee employee = new Employee("Updated Name", "HR", 75000);
        Employee result = repo.updateEmployee(99999, employee);
        assertThat(result).isNull();
    }

    @Test
    void shouldPersistUpdateedEmployeeData() {
        Employee employee = new Employee("UniqueTestName001", "IT", 50000);
        repo.saveEmployee(employee);
        
        List<Employee> allEmployees = repo.getAllEmployees();
        Employee savedEmployee = allEmployees.stream()
                .filter(e -> e.getName().equals("UniqueTestName001"))
                .findFirst()
                .orElse(null);
        
        assertThat(savedEmployee).isNotNull();
        int id = savedEmployee.getId();

        savedEmployee.setName("Modified Name");
        savedEmployee.setDepartment("HR");
        savedEmployee.setSalary(60000);
        
        repo.updateEmployee(id, savedEmployee);
        
        Employee retrievedEmployee = repo.getEmployeeById(id);
        assertThat(retrievedEmployee.getName()).isEqualTo("Modified Name");
        assertThat(retrievedEmployee.getDepartment()).isEqualTo("HR");
        assertThat(retrievedEmployee.getSalary()).isEqualTo(60000);
    }

    @Test
    void shouldAllowZeroSalary() {
        Employee employee = new Employee("Zero Salary Employee", "IT", 0);
        Employee savedEmployee = repo.saveEmployee(employee);
        assertThat(savedEmployee.getSalary()).isEqualTo(0);
    }

    @Test
    void shouldAllowNegativeSalary() {
        Employee employee = new Employee("Negative Salary Employee", "IT", -1000);
        Employee savedEmployee = repo.saveEmployee(employee);
        assertThat(savedEmployee.getSalary()).isEqualTo(-1000);
    }

    @Test
    void shouldAllowEmptyStringName() {
        Employee employee = new Employee("", "IT", 50000);
        Employee savedEmployee = repo.saveEmployee(employee);
        assertThat(savedEmployee.getName()).isEmpty();
    }

    @Test
    void shouldPreserveEmployeeAttributesAfterSave() {
        Employee employee = new Employee("John Doe", "Finance", 75000);
        Employee savedEmployee = repo.saveEmployee(employee);
        
        assertThat(savedEmployee.getName()).isEqualTo("John Doe");
        assertThat(savedEmployee.getDepartment()).isEqualTo("Finance");
        assertThat(savedEmployee.getSalary()).isEqualTo(75000);
    }

}
