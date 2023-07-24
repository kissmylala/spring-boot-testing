package kz.adem.springboottesting.service;

import kz.adem.springboottesting.exception.ResourceNotFoundException;
import kz.adem.springboottesting.model.Employee;
import kz.adem.springboottesting.repository.EmployeeRepository;
import kz.adem.springboottesting.service.impl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private  EmployeeRepository employeeRepository;
    @InjectMocks
    private  EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup(){
         employee = Employee.builder()
                .id(1L)
                .firstName("Adem")
                .lastName("Shanghai")
                .email("adem@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Junit test for saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
    //given
        given(employeeRepository.findEmployeeByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
    //when
        Employee savedEmployee = employeeService.saveEmployee(employee);
    //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getFirstName()).isEqualTo("Adem");

    }
    @Test
    @DisplayName("Junit test for saveEmployee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowException(){
        //given
        given(employeeRepository.findEmployeeByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.saveEmployee(employee);
        });
        //then
        verify(employeeRepository,never()).save(any(Employee.class));

    }

    @Test
    @DisplayName("Junit test for getAllEmployees method")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){

    //given
       Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("stark@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

    //when
        List<Employee> employees = employeeService.getAllEmployees();

    //then
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Junit test for getAllEmployees method (negative scenario)")
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){

        //given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("stark@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> employees = employeeService.getAllEmployees();

        //then
        assertThat(employees).isEmpty();
        assertThat(employees.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Junit test for getEmployeeById method")
    public void givenEmployeeId_whenFindById_thenReturnEmployeeObject(){
    //given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

    //when
        Employee employee1 = employeeService.getEmployeeById(employee.getId()).get();

    //then
        assertThat(employee1).isNotNull();
        assertThat(employee1.getId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("Junit test for getEmployeeById method in negative scenario")
    public void givenEmployeeIdNoExists_whenFindById_thenThrowException(){
        //given
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());

        //when
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,()->{
            Employee employee1 = employeeService.getEmployeeById(1L).get();
        });

        //then
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Junit test for UPDATE employee method")
    public void givenEmployeeObject_whenUpdateEmp_thenReturnUpdatedEmp(){
    //given
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("adem@gmail.com");
        employee.setFirstName("Ad");
    //when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

    //then
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo("adem@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ad");
    }

    @Test
    @DisplayName("Junit test for delete employee method")
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
    //given
        Long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

    //when
        employeeService.deleteEmployee(employeeId);
    //then
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }

}
