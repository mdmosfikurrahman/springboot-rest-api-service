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
        return createPersonResponse("You made a PUT request to update id = " + updatedPerson.getId() + " with the following data!", updatedPerson);
    }

    @Override
    public PersonResponse getPerson(Long id) {
        return personRepository.findById(id)
                .map(person -> createPersonResponse("Person retrieved!", person))
                .orElseGet(() -> createPersonResponse("Person with ID " + id + " not found.", null));
    }

    @Override
    public PersonResponse createPerson(PersonRequest request) {
        validator.validate(request);

        if (personRepository.existsByUserId(request.getUserId())) {
            throw new BadRequestException("A person with userId " + request.getUserId() + " already exists.");
        }

        if (!userService.userExists(request.getUserId())) {
            throw new NotFoundException("User with ID " + request.getUserId() + " does not exist.");
        }

        Person person = buildPersonForCreate(request);
        Person savedPerson = personRepository.save(person);
        return createPersonResponse("Person created successfully!", savedPerson);
    }

    @Override
    public PersonResponse updatePerson(Long id, PersonRequest request) {
        validator.validate(request);

        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with ID " + id + " not found."));

        if (!existingPerson.getUserId().equals(request.getUserId())) {
            if (personRepository.existsByUserId(request.getUserId())) {
                throw new BadRequestException("A person with userId " + request.getUserId() + " already exists.");
            }
        }

        return updateExistingPerson(existingPerson, request);
    }

    @Override
    public PersonResponse deletePerson(Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.deleteById(id);
                    return createPersonResponse("Person with ID " + id + " deleted successfully!", null);
                })
                .orElseGet(() -> createPersonResponse("Person with ID " + id + " not found.", null));
    }

}
