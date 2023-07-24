package kz.adem.springboottesting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.adem.springboottesting.model.Employee;
import kz.adem.springboottesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Junit test for create employee REST API")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
    //given
        Employee  employee = Employee.builder()
                .id(1L)
                .firstName("Adem")
                .lastName("Shanghai")
                .email("adem@gmail.com")
                .build();

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation)->invocation.getArgument(0));
    //when
       ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(employee)));
    //then

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
}

        @Test
        @DisplayName("Junit test for get All employees REST API")
        public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        //given
            List<Employee> listOfEmployees = new ArrayList<>();
            listOfEmployees.add(Employee.builder().firstName("Adem").lastName("Shanghai").email("adem@gmail.com").build());
            listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());
            BDDMockito.given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
        //when
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));
        //then
            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(2)));
        }

        @Test
        @DisplayName("Junit test for get employee by id REST API")
        public void givenId_whenGetEmpById_thenReturnEmployeeObject() throws Exception{
        //given
            Employee  employee = Employee.builder()
                    .id(1L)
                    .firstName("Adem")
                    .lastName("Shanghai")
                    .email("adem@gmail.com")
                    .build();
            BDDMockito.given(employeeService.getEmployeeById(1L)).willReturn(Optional.of(employee));

        //when
            ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employee.getId()));
            //then
            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(employee.getFirstName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(employee.getLastName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
        }

    @Test
    @DisplayName("Junit test for get employee by id in negative scenario REST API")
    public void givenInvalidId_whenGetEmpById_thenReturnEmpty() throws Exception{
        //given
        Employee  employee = Employee.builder()
                .id(1L)
                .firstName("Adem")
                .lastName("Shanghai")
                .email("adem@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(1L)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}",employee.getId()));
        //then
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("Junit test for update employees REST API - positive scenario")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
    //given
     Long employeeId = 1L;
        Employee  savedEmployee = Employee.builder()
                .id(1L)
                .firstName("Adem")
                .lastName("Shanghai")
                .email("adem@gmail.com")
                .build();
        Employee  updatedEmployee = Employee.builder()
                .firstName("Updated")
                .lastName("Updated")
                .email("updated@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->
                invocation.getArgument(0));
    //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)));

    //then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(updatedEmployee.getEmail())));
    }

    @Test
    @DisplayName("Junit test for update employees REST API - negative scenario")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        //given
        Long employeeId = 1L;
        Employee  savedEmployee = Employee.builder()
                .id(1L)
                .firstName("Adem")
                .lastName("Shanghai")
                .email("adem@gmail.com")
                .build();
        Employee  updatedEmployee = Employee.builder()
                .firstName("Updated")
                .lastName("Updated")
                .email("updated@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
//        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class))).willAnswer((invocation)->
//                invocation.getArgument(0));
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Junit test for delete employee REST API")
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
    //given
        Long empId = 1L;
        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(empId);
    //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}",empId));

    //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    }