package com.app.controller;

import com.app.dto.BlogDto;
import com.app.service.BlogService;
import com.app.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogDto> createBlog(
            @RequestParam(value = "blogPhoto", required = false) MultipartFile blogPhoto,
            @RequestParam("blogEnglishTitle") String blogEnglishTitle,
            @RequestParam("blogEnglishBody") String blogEnglishBody,
            @RequestParam(value = "englishShortDescription", required = false) String englishShortDescription,
            @RequestParam("blogArabicTitle") String blogArabicTitle,
            @RequestParam("blogArabicBody") String blogArabicBody,
            @RequestParam(value = "arabicShortDescription", required = false) String arabicShortDescription) {

        String photoUrl = null;
        if (blogPhoto != null && !blogPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(blogPhoto);
        }

        BlogDto blogDto = BlogDto.builder()
                .blogPhoto(photoUrl)
                .blogEnglishTitle(blogEnglishTitle)
                .blogEnglishBody(blogEnglishBody)
                .englishShortDescription(englishShortDescription)
                .blogArabicTitle(blogArabicTitle)
                .blogArabicBody(blogArabicBody)
                .arabicShortDescription(arabicShortDescription)
                .build();

        BlogDto created = blogService.createBlog(blogDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogDto> updateBlog(
            @PathVariable Long id,
            @RequestParam(value = "blogPhoto", required = false) MultipartFile blogPhoto,
            @RequestParam(value = "blogEnglishTitle", required = false) String blogEnglishTitle,
            @RequestParam(value = "blogEnglishBody", required = false) String blogEnglishBody,
            @RequestParam(value = "englishShortDescription", required = false) String englishShortDescription,
            @RequestParam(value = "blogArabicTitle", required = false) String blogArabicTitle,
            @RequestParam(value = "blogArabicBody", required = false) String blogArabicBody,
            @RequestParam(value = "arabicShortDescription", required = false) String arabicShortDescription) {

        String photoUrl = null;
        if (blogPhoto != null && !blogPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(blogPhoto);
        }

        BlogDto blogDto = BlogDto.builder()
                .blogPhoto(photoUrl)
                .blogEnglishTitle(blogEnglishTitle)
                .blogEnglishBody(blogEnglishBody)
                .englishShortDescription(englishShortDescription)
                .blogArabicTitle(blogArabicTitle)
                .blogArabicBody(blogArabicBody)
                .arabicShortDescription(arabicShortDescription)
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
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        List<BlogDto> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        BlogDto blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }
}
