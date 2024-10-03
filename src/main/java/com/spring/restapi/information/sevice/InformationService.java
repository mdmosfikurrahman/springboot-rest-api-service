package com.spring.restapi.information.sevice;

import com.spring.restapi.information.dto.request.InformationRequest;
import com.spring.restapi.information.dto.response.InformationResponse;

public interface InformationService {

    InformationResponse getInformation(Long id);
    InformationResponse createInformation(InformationRequest request);
    InformationResponse updateInformation(Long id, InformationRequest request);
    InformationResponse deleteInformation(Long id);

}
