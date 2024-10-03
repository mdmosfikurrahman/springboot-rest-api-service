package com.spring.restapi.information.sevice.impl;

import com.spring.restapi.information.dto.request.InformationRequest;
import com.spring.restapi.information.dto.response.InformationResponse;
import com.spring.restapi.information.model.Information;
import com.spring.restapi.information.repository.InformationRepository;
import com.spring.restapi.information.sevice.InformationService;
import com.spring.restapi.information.validator.InformationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {

    private final InformationRepository repository;
    private final InformationRequestValidator validator;

    private static final String INFORMATION_CREATED_MESSAGE = "Information with ID %s is created.";
    private static final String INFORMATION_UPDATED_MESSAGE = "Information with ID %s is updated.";
    private static final String INFORMATION_DELETED_MESSAGE = "Information with ID %s is deleted.";
    private static final String INFORMATION_RECEIVED_MESSAGE = "Information with ID %s is received.";
    private static final String INFORMATION_NOT_FOUND_MESSAGE = "Information with ID %s is not found.";

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

    private InformationResponse updateExistingInformation(Information existingInfo, InformationRequest request) {
        Information updatedInformation = buildInformationForUpdate(existingInfo, request);
        repository.save(updatedInformation);
        return createInformationResponse(String.format(INFORMATION_UPDATED_MESSAGE, updatedInformation.getId()), updatedInformation);
    }

    @Override
    public InformationResponse getInformation(Long id) {
        return repository.findById(id)
                .map(info -> createInformationResponse(String.format(INFORMATION_RECEIVED_MESSAGE, id), info))
                .orElseGet(() -> createInformationResponse(String.format(INFORMATION_NOT_FOUND_MESSAGE, id), null));
    }

    @Override
    public InformationResponse createInformation(InformationRequest request) {
        validator.validate(request);
        Information information = buildInformationForCreate(request);
        Information savedInformation = repository.save(information);
        return createInformationResponse(String.format(INFORMATION_CREATED_MESSAGE, savedInformation.getId()), savedInformation);
    }

    @Override
    public InformationResponse updateInformation(Long id, InformationRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingInfo -> updateExistingInformation(existingInfo, request))
                .orElseGet(() -> createInformationResponse(String.format(INFORMATION_NOT_FOUND_MESSAGE, id), null));
    }

    @Override
    public InformationResponse deleteInformation(Long id) {
        return repository.findById(id)
                .map(info -> {
                    repository.deleteById(id);
                    return createInformationResponse(String.format(INFORMATION_DELETED_MESSAGE, id), null);
                })
                .orElseGet(() -> createInformationResponse(String.format(INFORMATION_NOT_FOUND_MESSAGE, id), null));
    }

}
