package com.spring.restapi.information.service.impl;

import com.spring.restapi.common.exception.NotFoundException;
import com.spring.restapi.information.dto.request.InformationRequest;
import com.spring.restapi.information.dto.response.InformationResponse;
import com.spring.restapi.information.model.Information;
import com.spring.restapi.information.repository.InformationRepository;
import com.spring.restapi.information.service.InformationService;
import com.spring.restapi.information.validator.InformationRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {

    private final InformationRepository repository;
    private final InformationRequestValidator validator;

    private static final String INFORMATION_NOT_FOUND_MESSAGE = "Information with ID %s not found.";

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

    private InformationResponse createInformationResponse(Information info) {
        return InformationResponse.builder()
                .data(info)
                .build();
    }

    private InformationResponse updateExistingInformation(Information existingInfo, InformationRequest request) {
        Information updatedInformation = buildInformationForUpdate(existingInfo, request);
        repository.save(updatedInformation);
        return createInformationResponse(updatedInformation);
    }

    @Override
    public InformationResponse getInformation(Long id) {
        return repository.findById(id)
                .map(this::createInformationResponse)
                .orElseThrow(() -> new NotFoundException(String.format(INFORMATION_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public InformationResponse createInformation(InformationRequest request) {
        validator.validate(request);
        Information information = buildInformationForCreate(request);
        Information savedInformation = repository.save(information);
        return createInformationResponse(savedInformation);
    }

    @Override
    public InformationResponse updateInformation(Long id, InformationRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingInfo -> updateExistingInformation(existingInfo, request))
                .orElseThrow(() -> new NotFoundException(String.format(INFORMATION_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public void deleteInformation(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        information -> repository.deleteById(id),
                        () -> { throw new NotFoundException(String.format(INFORMATION_NOT_FOUND_MESSAGE, id)); }
                );
    }

}
