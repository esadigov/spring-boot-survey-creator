package com.example.survey.util;

import com.example.survey.model.general.PaginatedResponse;
import org.springframework.data.domain.Page;

public class DefaultMapping {

    public static PaginatedResponse mapSearchResponse(PaginatedResponse response, Page page){
        response.setPageSize(page.getSize());
        response.setPageNumber(page.getNumber());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }
}

