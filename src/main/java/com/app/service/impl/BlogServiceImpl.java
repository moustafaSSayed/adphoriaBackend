package com.app.service.impl;

import com.app.dto.BilingualField;
import com.app.dto.BlogDto;
import com.app.dto.PaginatedResponse;
import com.app.entity.BilingualText;
import com.app.entity.Blog;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.BlogRepository;
import com.app.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                .title(toBilingualText(blogDto.getTitle()))
                .body(toBilingualText(blogDto.getBody()))
                .shortDescription(toBilingualText(blogDto.getShortDescription()))
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
        if (blogDto.getTitle() != null) {
            blog.setTitle(mergeBilingualText(blog.getTitle(), blogDto.getTitle()));
        }
        if (blogDto.getBody() != null) {
            blog.setBody(mergeBilingualText(blog.getBody(), blogDto.getBody()));
        }
        if (blogDto.getShortDescription() != null) {
            blog.setShortDescription(mergeBilingualText(blog.getShortDescription(), blogDto.getShortDescription()));
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
    public PaginatedResponse<BlogDto> getAllBlogs(int page, int size) {
        Page<Blog> blogPage = blogRepository.findAll(PageRequest.of(page - 1, size));

        List<BlogDto> blogs = blogPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginatedResponse.PageMetadata metadata = PaginatedResponse.PageMetadata.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(blogPage.getTotalPages())
                .totalElements(blogPage.getTotalElements())
                .build();

        return PaginatedResponse.<BlogDto>builder()
                .data(blogs)
                .meta(metadata)
                .build();
    }

    @Override
    public BlogDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", id));
        return mapToDto(blog);
    }

    @Override
    public BlogDto getBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "slug", slug));
        return mapToDto(blog);
    }

    private BlogDto mapToDto(Blog blog) {
        return BlogDto.builder()
                .blogId(blog.getBlogId())
                .slug(blog.getSlug())
                .blogPhoto(blog.getBlogPhoto())
                .title(toBilingualField(blog.getTitle()))
                .body(toBilingualField(blog.getBody()))
                .shortDescription(toBilingualField(blog.getShortDescription()))
                .build();
    }

    private BilingualText toBilingualText(BilingualField field) {
        if (field == null)
            return null;
        return BilingualText.builder()
                .en(field.getEn())
                .ar(field.getAr())
                .build();
    }

    private BilingualText mergeBilingualText(BilingualText existing, BilingualField updates) {
        if (updates == null)
            return existing;

        // If no existing value, create new (for creation, not update)
        if (existing == null) {
            return toBilingualText(updates);
        }

        // Merge: use update value if provided, otherwise keep existing
        String newEn = updates.getEn() != null ? updates.getEn() : existing.getEn();
        String newAr = updates.getAr() != null ? updates.getAr() : existing.getAr();

        return BilingualText.builder()
                .en(newEn)
                .ar(newAr)
                .build();
    }

    private BilingualField toBilingualField(BilingualText text) {
        if (text == null)
            return null;
        return BilingualField.builder()
                .en(text.getEn())
                .ar(text.getAr())
                .build();
    }
}
