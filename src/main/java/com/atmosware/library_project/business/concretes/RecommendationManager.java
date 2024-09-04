package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.RecommendationService;
import com.atmosware.library_project.business.dtos.BookResponse;
import com.atmosware.library_project.core.utilities.mapping.BookMapper;
import com.atmosware.library_project.dataAccess.BookRepository;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.entities.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationManager implements RecommendationService {

    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;

    public List<BookResponse> recommendBooks(Long userId) {

        List<Book> borrowedBooks = transactionRepository.findBorrowedBooksByUserId(userId);

        List<Book> ratedBooks  = bookRepository.findBooksRatedByUserWithMinimumRating(userId, 3.5);

        if (ratedBooks.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> authors = ratedBooks.stream()
                .map(Book::getAuthor)
                .distinct()
                .toList();

        List<String> categories = ratedBooks.stream()
                .map(Book::getCategory)
                .distinct()
                .toList();

        List<Book> authorRecommendations = authors.stream()
                .flatMap(author -> bookRepository.findByAuthorAndMinimumRating(author, 3.5).stream())
                .distinct()
                .toList();

        List<Book> categoryRecommendations = categories.stream()
                .flatMap(category -> bookRepository.findByCategoryAndMinimumRating(category, 3.5).stream())
                .distinct()
                .toList();

        List<Book> allRecommendations = new ArrayList<>();
        allRecommendations.addAll(authorRecommendations);
        allRecommendations.addAll(categoryRecommendations);

        allRecommendations.removeAll(borrowedBooks);

        List<Book> uniqueRecommendations = allRecommendations.stream()
                .distinct()
                .toList();

        return uniqueRecommendations.stream()
                .map(BookMapper.INSTANCE::mapToResponse)
                .collect(Collectors.toList());
    }
}
