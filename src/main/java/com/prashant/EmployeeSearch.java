package com.prashant;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.jpa.repository.criteria.Specification;
import io.micronaut.http.annotation.QueryValue;
import jakarta.annotation.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Introspected
public class EmployeeSearch implements Specification<Employee> {

    @Nullable
    @QueryValue
    private String searchText;

    public EmployeeSearch(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String searchParam = "%" + searchText +"%";
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), searchParam)));
        query.where(predicates.get(0));
        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }

    public String getSearchText() {
        return searchText;
    }
}
