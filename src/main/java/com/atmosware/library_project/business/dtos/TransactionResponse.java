package com.atmosware.library_project.business.dtos;

import com.atmosware.library_project.entities.enums.Status;
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

    private List<Long> bookIds;
    private Long userId;    //TODO idlerin yerine name ya da email
    private LocalDateTime dueDate;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private Status status;
}
