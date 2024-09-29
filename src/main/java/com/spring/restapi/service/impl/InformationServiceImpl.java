package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;
import com.spring.restapi.model.Information;
import com.spring.restapi.repository.InformationRepository;
import com.spring.restapi.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {
    private final InformationRepository informationRepository;

    @Override
    public InformationResponse getInformation(Long id) {
        return informationRepository.findById(id)
                .map(info -> new InformationResponse("You made a GET request!", info))
                .orElse(null);
    }

    @Override
    public InformationResponse createInformation(InformationRequest request) {
        Information information = new Information();
        information.setName(request.getName());
        Information savedInformation = informationRepository.save(information);
        return new InformationResponse("You made a POST request with the following data!", savedInformation);
    }

    @Override
    public InformationResponse updateInformation(Long id, InformationRequest request) {
        Optional<Information> existingInformation = informationRepository.findById(id);
        if (existingInformation.isPresent()) {
            Information information = existingInformation.get();
            information.setName(request.getName());
            Information updatedInformation = informationRepository.save(information);
            return new InformationResponse("You made a PUT request to update id=" + id + " with the following data!", updatedInformation);
        }
        return null;
    }

    @Override
    public InformationResponse deleteInformation(Long id) {
        if (informationRepository.existsById(id)) {
            informationRepository.deleteById(id);
            return new InformationResponse("You made a DELETE request to delete id=" + id + "!", null);
        }
        return null;
    }

    @Override
    public List<InformationResponse> getAllInformation() {
        return informationRepository.findAll().stream()
                .map(info -> new InformationResponse("Information retrieved!", info))
                .collect(Collectors.toList());
    }

}
