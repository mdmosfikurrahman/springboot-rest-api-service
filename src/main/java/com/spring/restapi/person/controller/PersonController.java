package com.spring.restapi.person.controller;

import com.spring.restapi.common.response.RestResponse;
import com.spring.restapi.person.dto.request.PersonRequest;
import com.spring.restapi.person.dto.response.PersonResponse;
import com.spring.restapi.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping("/{id}")
    public RestResponse<PersonResponse> getPerson(@PathVariable Long id) {
        PersonResponse response = service.getPerson(id);
        return RestResponse.success(HttpStatus.OK.value(), "Request Successful", response);
    }

    @PostMapping
    public RestResponse<PersonResponse> createPerson(@RequestBody PersonRequest request) {
        PersonResponse response = service.createPerson(request);
        return RestResponse.success(HttpStatus.CREATED.value(), "Person created successfully", response);
    }

    @PutMapping("/{id}")
    public RestResponse<PersonResponse> updatePerson(@PathVariable Long id, @RequestBody PersonRequest request) {
        PersonResponse response = service.updatePerson(id, request);
        return RestResponse.success(HttpStatus.OK.value(), "Person updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deletePerson(@PathVariable Long id) {
        service.deletePerson(id);
        return RestResponse.success(HttpStatus.OK.value(), "Person deleted successfully", null);
    }

}
