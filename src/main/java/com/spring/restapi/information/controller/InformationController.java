package com.spring.restapi.information.controller;

import com.spring.restapi.common.response.RestResponse;
import com.spring.restapi.information.dto.request.InformationRequest;
import com.spring.restapi.information.dto.response.InformationResponse;
import com.spring.restapi.information.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InformationController {

    private final InformationService service;

    @GetMapping("/{id}")
    public RestResponse<InformationResponse> getInformation(@PathVariable Long id) {
        InformationResponse response = service.getInformation(id);
        return RestResponse.success(HttpStatus.OK.value(), "Request Successful", response);
    }

    @PostMapping
    public RestResponse<InformationResponse> createInformation(@RequestBody InformationRequest request) {
        InformationResponse response = service.createInformation(request);
        return RestResponse.success(HttpStatus.CREATED.value(), "Information created successfully", response);
    }

    @PutMapping("/{id}")
    public RestResponse<InformationResponse> updateInformation(@PathVariable Long id, @RequestBody InformationRequest request) {
        InformationResponse response = service.updateInformation(id, request);
        return RestResponse.success(HttpStatus.OK.value(), "Information updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteInformation(@PathVariable Long id) {
        service.deleteInformation(id);
        return RestResponse.success(HttpStatus.OK.value(), "Information deleted successfully", null);
    }
}
