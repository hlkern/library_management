package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.RecommendationService;
import com.atmosware.library_project.business.dtos.BookResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@AllArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<BookResponse> recommendBooks(@PathVariable Long userId) {
        return recommendationService.recommendBooks(userId);
    }
}
