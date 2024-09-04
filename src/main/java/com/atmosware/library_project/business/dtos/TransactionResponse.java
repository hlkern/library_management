package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse {

    private List<String> bookNames;
    private String userEmail;
    private LocalDateTime dueDate;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private BookStatus bookStatus;
}
