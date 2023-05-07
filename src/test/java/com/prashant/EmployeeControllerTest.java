package com.prashant;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class EmployeeControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void createEmployee() {
        HttpRequest<?> request = HttpRequest.POST("/emp", Collections.singletonMap("name", "Prashant"));
        HttpResponse<?> response = client.toBlocking().exchange(request);
        System.out.println(response);
        Long empId = entityId(response);
        assertNotNull(empId);
        request = HttpRequest.PUT("/emp/" + empId, Collections.singletonMap("name", "Brook"));
        response = client.toBlocking().exchange(request);
        request = HttpRequest.GET("/emp/" + empId);
        Employee employee = client.toBlocking().retrieve(request, Employee.class);
        assertEquals("Brook", employee.getName());

        URI uri = UriBuilder.of("/emp").queryParam("searchText", "B").build();
        request = HttpRequest.GET(uri);
        List employeeList = client.toBlocking().retrieve(request, Argument.of(List.class, Employee.class));
        assertEquals(1, employeeList.size());

    }

    protected Long entityId(HttpResponse<?> response) {
        String path = "/emp/";
        String value = response.header(HttpHeaders.LOCATION);
        if (value == null) {
            return null;
        }
        int index = value.indexOf(path);
        if (index != -1) {
            return Long.valueOf(value.substring(index + path.length()));
        }
        return null;
    }

}