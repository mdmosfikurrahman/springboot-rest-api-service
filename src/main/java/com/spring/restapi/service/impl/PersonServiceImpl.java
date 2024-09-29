package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.PersonRequest;
import com.spring.restapi.dto.response.PersonResponse;
import com.spring.restapi.model.Person;
import com.spring.restapi.repository.PersonRepository;
import com.spring.restapi.service.PersonService;
import com.spring.restapi.validator.PersonRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonRequestValidator validator;

    private Person buildPersonForCreate(PersonRequest request) {
        return Person.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }


    private PersonResponse createPersonResponse(String message, Person person) {
        return PersonResponse.builder()
                .message(message)
                .data(person)
                .build();
    }

    @Override
    public PersonResponse getPerson(Long id) {
        return repository.findById(id)
                .map(person -> createPersonResponse("Person retrieved!", person))
                .orElseGet(() -> createPersonResponse("Person with ID " + id + " not found.", null));
    }

    @Override
    public PersonResponse createPerson(PersonRequest request) {
        validator.validate(request);
        Person person = buildPersonForCreate(request);
        Person savedPerson = repository.save(person);
        return createPersonResponse("Person created successfully!", savedPerson);
    }

    @Override
    public PersonResponse updatePerson(Long id, PersonRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setFirstName(request.getFirstName());
                    existingPerson.setLastName(request.getLastName());
                    existingPerson.setUsername(request.getUsername());
                    existingPerson.setPassword(request.getPassword());
                    Person updatedPerson = repository.save(existingPerson);
                    return createPersonResponse("Person updated successfully!", updatedPerson);
                })
                .orElseGet(() -> createPersonResponse("Person with ID " + id + " not found.", null));
    }

    @Override
    public PersonResponse deletePerson(Long id) {
        return repository.findById(id)
                .map(person -> {
                    repository.deleteById(id);
                    return createPersonResponse("Person with ID " + id + " deleted successfully!", null);
                })
                .orElseGet(() -> createPersonResponse("Person with ID " + id + " not found.", null));
    }

    @Override
    public List<PersonResponse> getAllPersons() {
        List<Person> allPersons = repository.findAll();
        if (allPersons.isEmpty()) {
            return Collections.singletonList(createPersonResponse("No persons found.", null));
        }
        return allPersons.stream()
                .map(person -> createPersonResponse("Person retrieved!", person))
                .toList();
    }
}
