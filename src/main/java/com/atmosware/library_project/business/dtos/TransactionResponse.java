package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponse {

    private int id;
    private int bookId;
    private int userId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private Status status;
}
