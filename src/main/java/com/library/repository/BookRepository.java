package com.library.repository;

import com.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAllByIsborrowed(boolean status, PageRequest pageRequest);
}
