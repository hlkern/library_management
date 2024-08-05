package com.atmosware.library_project.entities;

import com.atmosware.library_project.core.entities.BaseEntity;
import com.atmosware.library_project.entities.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(name = "book_id")
    private int bookId; //TODO: bir kayıtta birden fazla kitap olabilir mi?

    @Column(name = "user_id")
    private int userId;

    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate; //TODO: kitap alma ve iade kısmını gözden geçir

    @Column(name = "status")
    private Status status;
}
