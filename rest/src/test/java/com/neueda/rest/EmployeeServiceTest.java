package com.neueda.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmpService service;
    @Mock
    EmpRepository repo;

    @Test
    void shouldGetAllEmployees() {
        List<Employee> list = List.of(
            new Employee("John Doe", "IT", 50000),
            new Employee("Jane Smith", "HR", 60000)
        );
        when(repo.getAllEmployees()).thenReturn(list);
        List<Employee> result = service.getAllEmployees();
        assertEquals(result.size(), 2);
        verify(repo).getAllEmployees();
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() {
        when(repo.getAllEmployees()).thenReturn(List.of());
        List<Employee> result = service.getAllEmployees();
        assertThat(result).hasSize(0);
        verify(repo).getAllEmployees();
    }

    @Test
    void shouldSaveEmployeeSuccessfully() {
        Employee employee = new Employee("John Doe", "IT", 50000);
        when(repo.saveEmployee(employee)).thenReturn(employee);
        int result = service.saveEmployee(employee);
        assertEquals(result, 1);
        verify(repo).saveEmployee(employee);
    }

    @Test
    void shouldReturnZeroWhenSaveEmployeeFails() {
        Employee employee = new Employee("John Doe", "IT", 50000);
        when(repo.saveEmployee(employee)).thenReturn(null);
        int result = service.saveEmployee(employee);
        assertEquals(result, 0);
        verify(repo).saveEmployee(employee);
    }

    @Test
    void shouldReturnEmployeeById() {
        Employee employee = new Employee("John Doe", "IT", 50000);
        employee.setId(1);
        when(repo.getEmployeeById(1)).thenReturn(employee);
        Employee result = service.getEmployeeById(1);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(repo).getEmployeeById(1);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFoundById() {
        when(repo.getEmployeeById(99999)).thenThrow(new EmployeeNotFoundException("Employee with ID 99999 not found."));
        assertThatThrownBy(() -> service.getEmployeeById(99999))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("Employee with ID 99999 not found.");
        verify(repo).getEmployeeById(99999);
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() {
        Employee employee = new Employee("Updated Name", "HR", 75000);
        when(repo.updateEmployee(1, employee)).thenReturn(employee);
        Employee result = service.updateEmpoyee(1, employee);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(repo).updateEmployee(1, employee);
    }

    @Test
    void shouldReturnNullWhenUpdateEmployeeFails() {
        Employee employee = new Employee("Updated Name", "HR", 75000);
        when(repo.updateEmployee(99999, employee)).thenReturn(null);
        Employee result = service.updateEmpoyee(99999, employee);
        assertThat(result).isNull();
        verify(repo).updateEmployee(99999, employee);
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() {
        when(repo.deleteEmployee(1)).thenReturn(1);
        int result = service.deleteEmployee(1);
        assertEquals(result, 1);
        verify(repo).deleteEmployee(1);
    }

    @Test
    void shouldReturnZeroWhenDeleteEmployeeFails() {
        when(repo.deleteEmployee(99999)).thenReturn(0);
        int result = service.deleteEmployee(99999);
        assertEquals(result, 0);
        verify(repo).deleteEmployee(99999);
    }

}


