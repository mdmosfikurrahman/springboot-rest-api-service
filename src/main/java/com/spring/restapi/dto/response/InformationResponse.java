package com.spring.restapi.dto.response;

import com.spring.restapi.model.Information;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformationResponse {

    private String message;
    private Information data;

}
