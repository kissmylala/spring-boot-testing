package kz.adem.springboottesting.repository;

import kz.adem.springboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//Repository integration tests with MySQL
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIT {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;
    @BeforeEach
    public void setup(){
        employee =  Employee.builder()
                .firstName("Adem")
                .lastName("Shanghai")
                .email("adem@gmail.com")
                .build();

    }

    //Junit test for save employee operation
    @Test
    @DisplayName("Junit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){



    //when - action or the behavior that we are going to test
        Employee savedEmployee  = employeeRepository.save(employee);

    // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }
    @Test
    @DisplayName("Junit test for get all employees")
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList(){

        Employee employee1 = Employee.builder()
                .firstName("Eugeny")
                .lastName("Onegin")
                .email("jenya@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Adem")
                .lastName("Shanghai")
                .email("ademshanghai@gmail.com")
                .build();
        //when
        employeeRepository.saveAll(List.of(employee1,employee2,employee));
        List<Employee> allEmployees = employeeRepository.findAll();
        //then
        assertThat(allEmployees).isNotNull();
        assertThat(allEmployees.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Junit test for get employee by ID")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){

        employeeRepository.save(employee);

        //when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(employeeDB).isNotNull();
    }
    @Test
    @DisplayName("Junit test for get employee by email operation")
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject(){

        employeeRepository.save(employee);
        //when
        Employee employee1 = employeeRepository.findEmployeeByEmail(employee.getEmail()).get();

        //then
        assertThat(employee1).isNotNull();
    }

    @Test
    @DisplayName("Junit test for update employee operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){

        employeeRepository.save(employee);
        //when
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("updated@gmail.com");
        savedEmployee.setFirstName("updated name");
        Employee updatedEmp =  employeeRepository.save(savedEmployee);

        //then
        assertThat(updatedEmp.getEmail()).isEqualTo("updated@gmail.com");
        assertThat(updatedEmp.getFirstName()).isEqualTo("updated name");
    }

    @Test
    @DisplayName("Junit test for delete employee operation")
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){

        employeeRepository.save(employee);
        //when
        employeeRepository.delete(employee);

        //then
        assertThat(employeeRepository.findById(employee.getId())).isEmpty();
    }
    @Test
    @DisplayName("Junit test for custom query using JPQL with index")
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){

        employeeRepository.save(employee);

        String firstName = "Ramesh";
        String lastName = "Fadatare";

        //when
        Employee savedEmp = employeeRepository.findByJPQL(firstName,lastName);

        //then
        assertThat(savedEmp).isNotNull();
    }
    @Test
    @DisplayName("Junit test for custom query using JPQL with named params")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){

        employeeRepository.save(employee);

        String firstName = "Ramesh";
        String lastName = "Fadatare";

        //when
        Employee savedEmp = employeeRepository.findByJPQLNamedParams(firstName,lastName);

        //then
        assertThat(savedEmp).isNotNull();
    }
    @DisplayName("Junit test for native SQL query with index")
    @Test
    public void givenFirstAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){

        employeeRepository.save(employee);

        String firstName = "Ramesh";
        String lastName = "Fadatare";

        //when
        Employee savedEmployee = employeeRepository.findByNativeSQL(firstName,lastName);


        //then
        assertThat(savedEmployee).isNotNull();
    }
    @DisplayName("Junit test for native SQL query with named params")
    @Test
    public void givenFirstAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){

        employeeRepository.save(employee);

        String firstName = "Ramesh";
        String lastName = "Fadatare";

        //when
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(firstName,lastName);


        //then
        assertThat(savedEmployee).isNotNull();
    }

}
