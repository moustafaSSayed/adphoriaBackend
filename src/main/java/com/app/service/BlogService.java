package com.app.service;

import com.app.dto.BlogDto;
import java.util.List;

public interface BlogService {

    BlogDto createBlog(BlogDto blogDto);

    BlogDto updateBlog(Long id, BlogDto blogDto);

    void deleteBlog(Long id);

    List<BlogDto> getAllBlogs();

    BlogDto getBlogById(Long id);
}
