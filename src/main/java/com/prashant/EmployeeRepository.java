package com.prashant;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.jpa.repository.JpaSpecificationExecutor;
import io.micronaut.data.repository.CrudRepository;
import org.hibernate.dialect.Dialect;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Employee save(@NotNull @NotBlank String name);
}
