package com.library.service;

import com.library.model.Book;
import com.library.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface BookService {

    Book save(BookRequest bookRequest);

    Page<Book> getAll(boolean status, Integer page, Integer size);

    ResponseEntity<?> getById(Long id);

    Book updateBook(Long id, BookRequest bookRequest);

    Book changeStatus(Long id, boolean status);
}
