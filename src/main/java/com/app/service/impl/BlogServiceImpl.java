package com.app.service.impl;

import com.app.dto.BlogDto;
import com.app.entity.Blog;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.BlogRepository;
import com.app.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public BlogDto createBlog(BlogDto blogDto) {
        Blog blog = Blog.builder()
                .blogPhoto(blogDto.getBlogPhoto())
                .blogEnglishTitle(blogDto.getBlogEnglishTitle())
                .blogEnglishBody(blogDto.getBlogEnglishBody())
                .englishShortDescription(blogDto.getEnglishShortDescription())
                .blogArabicTitle(blogDto.getBlogArabicTitle())
                .blogArabicBody(blogDto.getBlogArabicBody())
                .arabicShortDescription(blogDto.getArabicShortDescription())
                .build();

        Blog saved = blogRepository.save(blog);
        return mapToDto(saved);
    }

    @Override
    public BlogDto updateBlog(Long id, BlogDto blogDto) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", id));

        // Only update fields that are not null (partial update support)
        if (blogDto.getBlogPhoto() != null) {
            blog.setBlogPhoto(blogDto.getBlogPhoto());
        }
        if (blogDto.getBlogEnglishTitle() != null) {
            blog.setBlogEnglishTitle(blogDto.getBlogEnglishTitle());
        }
        if (blogDto.getBlogEnglishBody() != null) {
            blog.setBlogEnglishBody(blogDto.getBlogEnglishBody());
        }
        if (blogDto.getEnglishShortDescription() != null) {
            blog.setEnglishShortDescription(blogDto.getEnglishShortDescription());
        }
        if (blogDto.getBlogArabicTitle() != null) {
            blog.setBlogArabicTitle(blogDto.getBlogArabicTitle());
        }
        if (blogDto.getBlogArabicBody() != null) {
            blog.setBlogArabicBody(blogDto.getBlogArabicBody());
        }
        if (blogDto.getArabicShortDescription() != null) {
            blog.setArabicShortDescription(blogDto.getArabicShortDescription());
        }

        Blog updated = blogRepository.save(blog);
        return mapToDto(updated);
    }

    @Override
    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", id));
        blogRepository.delete(blog);
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDto

    getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", id));
        return mapToDto(blog);
    }

    private BlogDto mapToDto(Blog blog) {
        return BlogDto.builder()
                .blogId(blog.getBlogId())
                .blogPhoto(blog.getBlogPhoto())
                .blogEnglishTitle(blog.getBlogEnglishTitle())
                .blogEnglishBody(blog.getBlogEnglishBody())
                .englishShortDescription(blog.getEnglishShortDescription())
                .blogArabicTitle(blog.getBlogArabicTitle())
                .blogArabicBody(blog.getBlogArabicBody())
                .arabicShortDescription(blog.getArabicShortDescription())
                .build();
    }
}
