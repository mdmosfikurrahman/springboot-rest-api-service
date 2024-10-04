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
                .orElseGet(() -> createInformationResponse(null));
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
                .orElseGet(() -> createInformationResponse(null));
    }

    @Override
    public void deleteInformation(Long id) {
        repository.findById(id)
                .map(info -> {
                    repository.deleteById(id);
                    return createInformationResponse(null);
                })
                .orElseGet(() -> createInformationResponse(null));
    }

}
