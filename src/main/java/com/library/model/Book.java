package com.library.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String description;
    private String category;
    private Date releaseDate;
    private boolean isborrowed = false;

    public Book() {
    }

    public Book(Long id, String title, String author, String description, String category, Date releaseDate, boolean isborrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.category = category;
        this.releaseDate = releaseDate;
        this.isborrowed = isborrowed;
    }
}
