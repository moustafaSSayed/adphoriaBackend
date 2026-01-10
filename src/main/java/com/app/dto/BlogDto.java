package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDto {
    private Long blogId;
    private String slug;
    private String blogPhoto;
    private BilingualField title;
    private BilingualField body;
    private BilingualField shortDescription;
}
