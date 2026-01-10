package com.app.controller;

import com.app.dto.BilingualField;
import com.app.dto.BlogDto;
import com.app.dto.PaginatedResponse;
import com.app.service.BlogService;
import com.app.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogDto> createBlog(
            @RequestParam(value = "blogPhoto", required = false) MultipartFile blogPhoto,
            @RequestParam("titleEn") String titleEn,
            @RequestParam("titleAr") String titleAr,
            @RequestParam("bodyEn") String bodyEn,
            @RequestParam("bodyAr") String bodyAr,
            @RequestParam(value = "shortDescriptionEn", required = false) String shortDescriptionEn,
            @RequestParam(value = "shortDescriptionAr", required = false) String shortDescriptionAr) {

        String photoUrl = null;
        if (blogPhoto != null && !blogPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(blogPhoto);
        }

        BlogDto blogDto = BlogDto.builder()
                .blogPhoto(photoUrl)
                .title(BilingualField.builder().en(titleEn).ar(titleAr).build())
                .body(BilingualField.builder().en(bodyEn).ar(bodyAr).build())
                .shortDescription(BilingualField.builder().en(shortDescriptionEn).ar(shortDescriptionAr).build())
                .build();

        BlogDto created = blogService.createBlog(blogDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogDto> updateBlog(
            @PathVariable Long id,
            @RequestParam(value = "blogPhoto", required = false) MultipartFile blogPhoto,
            @RequestParam(value = "titleEn", required = false) String titleEn,
            @RequestParam(value = "titleAr", required = false) String titleAr,
            @RequestParam(value = "bodyEn", required = false) String bodyEn,
            @RequestParam(value = "bodyAr", required = false) String bodyAr,
            @RequestParam(value = "shortDescriptionEn", required = false) String shortDescriptionEn,
            @RequestParam(value = "shortDescriptionAr", required = false) String shortDescriptionAr) {

        String photoUrl = null;
        if (blogPhoto != null && !blogPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(blogPhoto);
        }

        BlogDto blogDto = BlogDto.builder()
                .blogPhoto(photoUrl)
                .title(titleEn != null ? BilingualField.builder().en(titleEn).ar(titleAr).build() : null)
                .body(bodyEn != null ? BilingualField.builder().en(bodyEn).ar(bodyAr).build() : null)
                .shortDescription(shortDescriptionEn != null
                        ? BilingualField.builder().en(shortDescriptionEn).ar(shortDescriptionAr).build()
                        : null)
                .build();

        BlogDto updated = blogService.updateBlog(id, blogDto);
        return ResponseEntity.ok(updated);
    }

    // JSON endpoint for update without file upload
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BlogDto> updateBlogJson(@PathVariable Long id, @RequestBody BlogDto blogDto) {
        BlogDto updated = blogService.updateBlog(id, blogDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<BlogDto>> getAllBlogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size) {
        PaginatedResponse<BlogDto> blogs = blogService.getAllBlogs(page, size);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        BlogDto blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<BlogDto> getBlogBySlug(@PathVariable String slug) {
        BlogDto blog = blogService.getBlogBySlug(slug);
        return ResponseEntity.ok(blog);
    }
}
