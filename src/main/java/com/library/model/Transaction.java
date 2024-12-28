package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private boolean late;

    @ManyToMany
    @JsonIgnore
    private List<Book> books;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Transaction() {
    }

    public Transaction(Long id, Date borrowDate, Date dueDate, Date returnDate, boolean late, List<Book> books, Customer customer) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.late = late;
        this.books = books;
        this.customer = customer;
    }
}
