package com.spring.restapi.controller;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.dto.response.InformationResponse;
import com.spring.restapi.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InformationController {
    private final InformationService service;

    @GetMapping("/{id}")
    public ResponseEntity<InformationResponse> getInformation(@PathVariable Long id) {
        InformationResponse response = service.getInformation(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
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
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InformationResponse> patchInformation(@PathVariable Long id, @RequestBody InformationRequest request) {
        InformationResponse response = service.patchInformation(id, request);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInformation(@PathVariable Long id) {
        if (!service.deleteInformation(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<InformationResponse>> getAllInformation() {
        List<InformationResponse> allInformation = service.getAllInformation();
        return ResponseEntity.ok(allInformation);
    }
}
