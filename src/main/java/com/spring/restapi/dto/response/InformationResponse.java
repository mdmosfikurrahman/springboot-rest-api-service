package com.spring.restapi.dto.response;

import com.spring.restapi.model.Information;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationResponse {
    private String message;
    private Information data;
}
