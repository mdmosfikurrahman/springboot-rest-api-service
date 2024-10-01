package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.PersonRequest;
import com.spring.restapi.dto.response.PersonResponse;
import com.spring.restapi.exception.BadRequestException;
import com.spring.restapi.exception.NotFoundException;
import com.spring.restapi.model.Person;
import com.spring.restapi.repository.PersonRepository;
import com.spring.restapi.service.PersonService;
import com.spring.restapi.service.UserService;
import com.spring.restapi.validator.PersonRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final UserService userService;
    private final PersonRequestValidator validator;

    private static final String PERSON_CREATED_MESSAGE = "Person with ID %s is created.";
    private static final String PERSON_UPDATED_MESSAGE = "Person with ID %s is updated.";
    private static final String PERSON_DELETED_MESSAGE = "Person with ID %s is deleted.";
    private static final String PERSON_RECEIVED_MESSAGE = "Person with ID %s is received.";
    private static final String PERSON_NOT_FOUND_MESSAGE = "Person with ID %s not found.";
    private static final String PERSON_EXISTS_MESSAGE = "A person with userId %s already exists.";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s is not found.";

    private Person buildPersonForCreate(PersonRequest request) {
        return Person.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }

    private Person buildPersonForUpdate(Person existingPerson, PersonRequest request) {
        return Person.builder()
                .id(existingPerson.getId())
                .userId(request.getUserId())
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

    private PersonResponse updateExistingPerson(Person existingPerson, PersonRequest request) {
        Person updatedPerson = buildPersonForUpdate(existingPerson, request);
        personRepository.save(updatedPerson);
        return createPersonResponse(String.format(PERSON_UPDATED_MESSAGE, updatedPerson.getId()), updatedPerson);
    }

    @Override
    public PersonResponse getPerson(Long id) {
        return personRepository.findById(id)
                .map(person -> createPersonResponse(String.format(PERSON_RECEIVED_MESSAGE, id), person))
                .orElseGet(() -> createPersonResponse(String.format(PERSON_NOT_FOUND_MESSAGE, id), null));
    }

    @Override
    public PersonResponse createPerson(PersonRequest request) {
        validator.validate(request);

        if (personRepository.existsByUserId(request.getUserId())) {
            throw new BadRequestException(String.format(PERSON_EXISTS_MESSAGE, request.getUserId()));
        }

        if (!userService.userExists(request.getUserId())) {
            throw new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, request.getUserId()));
        }

        Person person = buildPersonForCreate(request);
        Person savedPerson = personRepository.save(person);
        return createPersonResponse(String.format(PERSON_CREATED_MESSAGE, savedPerson.getId()), savedPerson);
    }

    @Override
    public PersonResponse updatePerson(Long id, PersonRequest request) {
        validator.validate(request);

        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND_MESSAGE, id)));

        if (!existingPerson.getUserId().equals(request.getUserId()) && personRepository.existsByUserId(request.getUserId())) {
            throw new BadRequestException(String.format(PERSON_EXISTS_MESSAGE, request.getUserId()));
        }

        return updateExistingPerson(existingPerson, request);
    }

    @Override
    public PersonResponse deletePerson(Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.deleteById(id);
                    return createPersonResponse(String.format(PERSON_DELETED_MESSAGE, id), null);
                })
                .orElseGet(() -> createPersonResponse(String.format(PERSON_NOT_FOUND_MESSAGE, id), null));
    }

}
