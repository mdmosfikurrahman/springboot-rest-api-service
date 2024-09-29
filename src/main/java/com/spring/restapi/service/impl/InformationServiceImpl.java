package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;
import com.spring.restapi.model.Information;
import com.spring.restapi.repository.InformationRepository;
import com.spring.restapi.service.InformationService;
import com.spring.restapi.validator.InformationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {

    private final InformationRepository repository;
    private final InformationRequestValidator validator;

    private Information buildInformationForCreate(InformationRequest request) {
        return Information.builder()
                .name(request.getName())
                .build();
    }

    private Information buildInformationForUpdate(Information information, InformationRequest request) {
        return Information.builder()
                .id(information.getId())
                .name(request.getName())
                .build();
    }

    private InformationResponse createInformationResponse(String message, Information info) {
        return InformationResponse.builder()
                .message(message)
                .data(info)
                .build();
    }

    @Override
    public InformationResponse getInformation(Long id) {
        return repository.findById(id)
                .map(info -> createInformationResponse("Information retrieved!", info))
                .orElseGet(() -> createInformationResponse("Information with ID " + id + " not found.", null));
    }

    @Override
    public InformationResponse createInformation(InformationRequest request) {
        validator.validate(request);
        Information information = buildInformationForCreate(request);
        Information savedInformation = repository.save(information);
        return createInformationResponse("You made a POST request with the following data!", savedInformation);
    }

    @Override
    public InformationResponse updateInformation(Long id, InformationRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingInfo -> updateExistingInformation(existingInfo, request))
                .orElseGet(() -> createInformationResponse("Information with ID " + id + " not found.", null));
    }

    private InformationResponse updateExistingInformation(Information existingInfo, InformationRequest request) {
        Information updatedInformation = buildInformationForUpdate(existingInfo, request);
        repository.save(updatedInformation);
        return createInformationResponse("You made a PUT request to update id = " + updatedInformation.getId() + " with the following data!", updatedInformation);
    }

    @Override
    public InformationResponse deleteInformation(Long id) {
        return repository.findById(id)
                .map(info -> {
                    repository.deleteById(id);
                    return createInformationResponse("You made a DELETE request to delete id = " + id + "!", null);
                })
                .orElseGet(() -> createInformationResponse("Information with ID " + id + " not found.", null));
    }

}
