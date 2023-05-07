package com.prashant;

import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    Employee createEmployee(String name){
        return employeeRepository.save(name);
    }

    Employee updateEmployee(@NotNull Long id, @NotNull @NotBlank String name) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setName(name);
            return employeeRepository.update(employee);
        }
        else{
            throw new RuntimeException("No Records Found");
        }
    }

    Employee employeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else{
            throw new RuntimeException("No Records Found");
        }
    }

    public List<Employee> searchEmployees(EmployeeSearch employeeSearch) {
        return employeeRepository.findAll(employeeSearch);
    }
}
