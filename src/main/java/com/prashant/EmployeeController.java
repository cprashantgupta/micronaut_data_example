package com.prashant;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.net.URI;
import java.util.List;

@Controller("/emp")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Post
    HttpResponse<Employee> createEmployee(String name){
        Employee employee = employeeService.createEmployee(name);
        return HttpResponse.created(employee).headers(headers -> headers.location(location(employee.getId())));
    }

    @Put("/{id}")
    HttpResponse<Employee> updateEmployee(@PathVariable Long id, String name){
        Employee employee = employeeService.updateEmployee(id, name);
        return HttpResponse.created(employee).headers(headers -> headers.location(location(employee.getId())));
    }

    @Get("/{id}")
    Employee employeeById(@PathVariable Long id){
        return employeeService.employeeById(id);
    }

    @Get
    List<Employee> searchEmployees(@RequestBean EmployeeSearch employeeSearch){
        return employeeService.searchEmployees(employeeSearch);
    }

    URI location(Long id){
        return URI.create("/emp/"+id);
    }

}
