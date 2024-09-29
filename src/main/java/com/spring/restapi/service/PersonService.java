package com.spring.restapi.service;

import com.spring.restapi.dto.request.PersonRequest;
import com.spring.restapi.dto.response.PersonResponse;

import java.util.List;

public interface PersonService {

    PersonResponse getPerson(Long id);
    PersonResponse createPerson(PersonRequest request);
    PersonResponse updatePerson(Long id, PersonRequest request);
    PersonResponse deletePerson(Long id);
    List<PersonResponse> getAllPersons();

}
