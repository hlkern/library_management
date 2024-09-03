package com.atmosware.library_project.business.abstracts;

import com.atmosware.library_project.business.dtos.BookResponse;
import java.util.List;

public interface RecommendationService {

    List<BookResponse> recommendBooks(Long userId);
}
