package com.spring.restapi.service;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;

import java.util.List;

public interface InformationService {
    InformationResponse getInformation(Long id);
    InformationResponse createInformation(InformationRequest request);
    InformationResponse updateInformation(Long id, InformationRequest request);
    InformationResponse patchInformation(Long id, InformationRequest request); // New method for PATCH
    boolean deleteInformation(Long id);
    List<InformationResponse> getAllInformation();
}
