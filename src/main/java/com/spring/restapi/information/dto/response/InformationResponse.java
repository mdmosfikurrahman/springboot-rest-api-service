package com.spring.restapi.information.dto.response;

import com.spring.restapi.information.model.Information;
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
