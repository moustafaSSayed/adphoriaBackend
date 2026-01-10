package com.app.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedResponse<T> {
    private List<T> data;
    private PageMetadata meta;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PageMetadata {
        private int currentPage;
        private int pageSize;
        private int totalPages;
        private long totalElements;
    }
}
