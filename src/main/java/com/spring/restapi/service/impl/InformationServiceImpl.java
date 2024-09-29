package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;
import com.spring.restapi.model.Information;
import com.spring.restapi.repository.InformationRepository;
import com.spring.restapi.service.InformationService;
import com.spring.restapi.validator.InformationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {

    private final InformationRepository repository;
    private final InformationRequestValidator validator;

    @Override
    public InformationResponse getInformation(Long id) {
        return repository.findById(id)
                .map(this::createInformationResponseForGet)
                .orElse(InformationResponse.builder()
                        .message("Information with ID " + id + " not found.")
                        .data(null)
                        .build());
    }


    @Override
    public InformationResponse createInformation(InformationRequest request) {
        validator.validate(request);
        Information information = buildInformation(request);
        Information savedInformation = repository.save(information);
        return createInformationResponseForPost(savedInformation);
    }

    @Override
    public InformationResponse updateInformation(Long id, InformationRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingInfo -> updateExistingInformation(existingInfo, request))
                .orElse(null);
    }

    @Override
    public InformationResponse deleteInformation(Long id) {
        return repository.findById(id)
                .map(info -> {
                    repository.deleteById(id);
                    return InformationResponse.builder()
                            .message("You made a DELETE request to delete id = " + id + "!")
                            .data(null)
                            .build();
                })
                .orElse(InformationResponse.builder()
                        .message("Information with ID " + id + " not found.")
                        .data(null)
                        .build());
    }

    @Override
    public List<InformationResponse> getAllInformation() {
        List<Information> informationList = repository.findAll();

        if (informationList.isEmpty()) {
            return Collections.singletonList(InformationResponse.builder()
                    .message("No information records found.")
                    .data(null)
                    .build());
        }

        return informationList.stream()
                .map(this::createInformationResponseForGet)
                .collect(Collectors.toList());
    }


    private Information buildInformation(InformationRequest request) {
        return Information.builder()
                .name(request.getName())
                .build();
    }

    private InformationResponse createInformationResponseForGet(Information info) {
        return InformationResponse.builder()
                .message("Information retrieved!")
                .data(info)
                .build();
    }

    private InformationResponse createInformationResponseForPost(Information info) {
        return InformationResponse.builder()
                .message("You made a POST request with the following data!")
                .data(info)
                .build();
    }

    private InformationResponse updateExistingInformation(Information existingInfo, InformationRequest request) {
        Information updatedInformation = Information.builder()
                .id(existingInfo.getId())
                .name(request.getName())
                .build();
        repository.save(updatedInformation);
        return InformationResponse.builder()
                .message("You made a PUT request to update id = " + updatedInformation.getId() + " with the following data!")
                .data(updatedInformation)
                .build();
    }
}
