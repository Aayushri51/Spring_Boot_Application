package com.neueda.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTEst {
    @InjectMocks
    EmpController empController;

    @Mock
    EmpService empService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(empController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {
        List<Employee> list = List.of(
                new Employee("John Doe", "IT", 50000),
                new Employee("Jane Smith", "HR", 60000)
        );
        when(empService.getAllEmployees()).thenReturn(list);

        mockMvc.perform(get("/api/v1/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employees fetched successfully"))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"))
                .andExpect(jsonPath("$.data[1].name").value("Jane Smith"));
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() throws Exception {
        when(empService.getAllEmployees()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employees fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        Employee employee = new Employee("John Doe", "IT", 50000);
        employee.setId(1);
        when(empService.getEmployeeById(1)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee fetched successfully"))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.department").value("IT"));
    }

    @Test
    void shouldReturnNotFoundWhenEmployeeDoesNotExist() throws Exception {
        when(empService.getEmployeeById(99999)).thenThrow(
                new EmployeeNotFoundException("Employee with ID 99999 not found.")
        );

        mockMvc.perform(get("/api/v1/employees/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Employee with ID 99999 not found."));
    }

    @Test
    void shouldCreateEmployeeSuccessfully() throws Exception {
        Employee employee = new Employee("John Doe", "IT", 50000);
        when(empService.saveEmployee(any(Employee.class))).thenReturn(1);

        mockMvc.perform(post("/api/v1/employees/")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Employee added successfully"))
                .andExpect(jsonPath("$.data.name").value("John Doe"));
    }

    @Test
    void shouldReturnBadRequestWhenSaveEmployeeFails() throws Exception {
        Employee employee = new Employee("John Doe", "IT", 50000);
        when(empService.saveEmployee(any(Employee.class))).thenReturn(0);

        mockMvc.perform(post("/api/v1/employees/")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Employee not added"));
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() throws Exception {
        Employee employee = new Employee("Updated Name", "HR", 75000);
        when(empService.saveEmployee(any(Employee.class))).thenReturn(1);

        mockMvc.perform(put("/api/v1/employees/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee updated successfully"))
                .andExpect(jsonPath("$.data.name").value("Updated Name"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateEmployeeFails() throws Exception {
        Employee employee = new Employee("Updated Name", "HR", 75000);
        when(empService.saveEmployee(any(Employee.class))).thenReturn(0);

        mockMvc.perform(put("/api/v1/employees/99999")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Employee not updated"));
    }

    @Test
    void shouldDeleteEmployeeSuccessfully() throws Exception {
        when(empService.deleteEmployee(1)).thenReturn(1);

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Employee deleted successfully"));
    }

    @Test
    void shouldReturnBadRequestWhenDeleteEmployeeFails() throws Exception {
        when(empService.deleteEmployee(99999)).thenReturn(0);

        mockMvc.perform(delete("/api/v1/employees/99999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Employee not deleted"));
    }

}