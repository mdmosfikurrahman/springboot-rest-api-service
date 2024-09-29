package com.spring.restapi.service;

import com.spring.restapi.dto.request.PersonRequest;
import com.spring.restapi.dto.response.PersonResponse;

public interface PersonService {

    PersonResponse getPerson(Long id);
    PersonResponse createPerson(PersonRequest request);
    PersonResponse updatePerson(Long id, PersonRequest request);
    PersonResponse deletePerson(Long id);

}
