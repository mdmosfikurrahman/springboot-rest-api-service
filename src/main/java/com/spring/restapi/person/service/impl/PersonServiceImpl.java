package com.spring.restapi.person.service.impl;

import com.spring.restapi.person.dto.request.PersonRequest;
import com.spring.restapi.person.dto.response.PersonResponse;
import com.spring.restapi.common.exception.BadRequestException;
import com.spring.restapi.common.exception.NotFoundException;
import com.spring.restapi.person.model.Person;
import com.spring.restapi.person.repository.PersonRepository;
import com.spring.restapi.person.service.PersonService;
import com.spring.restapi.user.service.UserService;
import com.spring.restapi.person.validator.PersonRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final UserService userService;
    private final PersonRequestValidator validator;
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

    private PersonResponse createPersonResponse(Person person) {
        return PersonResponse.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();
    }

    private PersonResponse updateExistingPerson(Person existingPerson, PersonRequest request) {
        Person updatedPerson = buildPersonForUpdate(existingPerson, request);
        personRepository.save(updatedPerson);
        return createPersonResponse(updatedPerson);
    }

    @Override
    public PersonResponse getPerson(Long id) {
        return personRepository.findById(id)
                .map(this::createPersonResponse)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND_MESSAGE, id)));
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
        return createPersonResponse(savedPerson);
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
    public void deletePerson(Long id) {
        personRepository.findById(id)
                .map(person -> {
                    personRepository.deleteById(id);
                    return createPersonResponse(null);
                })
                .orElseGet(() -> createPersonResponse(null));
    }

}
