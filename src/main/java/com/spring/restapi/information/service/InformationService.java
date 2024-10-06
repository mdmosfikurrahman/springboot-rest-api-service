package com.spring.restapi.information.service;

import com.spring.restapi.information.dto.request.InformationRequest;
import com.spring.restapi.information.dto.response.InformationResponse;

public interface InformationService {

    InformationResponse getInformation(Long id);
    InformationResponse createInformation(InformationRequest request);
    InformationResponse updateInformation(Long id, InformationRequest request);
    void deleteInformation(Long id);

}
