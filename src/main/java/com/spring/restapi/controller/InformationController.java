package com.spring.restapi.controller;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;
import com.spring.restapi.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InformationController {

    private final InformationService service;

    @GetMapping("/{id}")
    public ResponseEntity<InformationResponse> getInformation(@PathVariable Long id) {
        InformationResponse response = service.getInformation(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<InformationResponse> createInformation(@RequestBody InformationRequest request) {
        InformationResponse response = service.createInformation(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InformationResponse> updateInformation(@PathVariable Long id, @RequestBody InformationRequest request) {
        InformationResponse response = service.updateInformation(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InformationResponse> deleteInformation(@PathVariable Long id) {
        InformationResponse response = service.deleteInformation(id);
        return ResponseEntity.ok(response);
    }

}
