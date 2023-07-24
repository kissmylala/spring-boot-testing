package kz.adem.springboottesting.service;

import kz.adem.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
     Employee saveEmployee(Employee employee);
     List<Employee> getAllEmployees();
     Optional <Employee> getEmployeeById(Long id);
     Employee updateEmployee(Employee employee);
     void deleteEmployee(Long id);
}
