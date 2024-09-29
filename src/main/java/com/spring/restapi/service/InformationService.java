package com.spring.restapi.service;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;

public interface InformationService {

    InformationResponse getInformation(Long id);
    InformationResponse createInformation(InformationRequest request);
    InformationResponse updateInformation(Long id, InformationRequest request);
    InformationResponse deleteInformation(Long id);

}
