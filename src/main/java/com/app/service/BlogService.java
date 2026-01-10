package com.app.service;

import com.app.dto.BlogDto;
import com.app.dto.PaginatedResponse;

public interface BlogService {

    BlogDto createBlog(BlogDto blogDto);

    BlogDto updateBlog(Long id, BlogDto blogDto);

    void deleteBlog(Long id);

    PaginatedResponse<BlogDto> getAllBlogs(int page, int size);

    BlogDto getBlogById(Long id);

    BlogDto getBlogBySlug(String slug);
}
