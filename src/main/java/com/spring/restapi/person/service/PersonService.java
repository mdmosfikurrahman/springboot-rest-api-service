package com.spring.restapi.person.service;

import com.spring.restapi.person.dto.request.PersonRequest;
import com.spring.restapi.person.dto.response.PersonResponse;

public interface PersonService {

    PersonResponse getPerson(Long id);
    PersonResponse createPerson(PersonRequest request);
    PersonResponse updatePerson(Long id, PersonRequest request);
    void deletePerson(Long id);

}
