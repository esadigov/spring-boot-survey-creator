package com.example.survey.model.general;


import lombok.Data;

@Data
public class PaginatedResponse<T> {
    T data;
    int pageSize;
    int pageNumber;
    int numberOfElements;
    int totalPages;
    long totalElements;

}
