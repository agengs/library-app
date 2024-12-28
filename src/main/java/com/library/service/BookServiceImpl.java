package com.library.service;

import com.library.model.Book;
import com.library.model.Transaction;
import com.library.repository.BookRepository;
import com.library.repository.TransactionRepository;
import com.library.request.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl  implements BookService{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Book save(BookRequest bookRequest) {
        Book newBook = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .description(bookRequest.getDescription())
                .category(bookRequest.getCategory())
                .releaseDate(bookRequest.getReleaseDate())
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Page<Book> getAll(boolean status, Integer page, Integer size) {
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Book> filterBooks = bookRepository.findAllByIsborrowed(status, pageRequest);


        return filterBooks;
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Map<String, Object> result = new HashMap<>();
        Book book = bookRepository.findById(id).orElse(null);
        if(book == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        List<Transaction> transactions = transactionRepository.findByBookIdInList(id);

        result.put("message", "Get books detail ");
        result.put("bookDetail", book);
        result.put("listTransaction", transactions);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public Book updateBook(Long id, BookRequest bookRequest) {
        Optional<Book> getBook = bookRepository.findById(id);
        if(getBook.isPresent()){
            getBook.get().setTitle(bookRequest.getTitle());
            getBook.get().setAuthor(bookRequest.getAuthor());
            getBook.get().setDescription(bookRequest.getDescription());
            getBook.get().setCategory(bookRequest.getCategory());
            getBook.get().setReleaseDate(bookRequest.getReleaseDate());

            return bookRepository.save(getBook.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    @Override
    public Book changeStatus(Long id, boolean status) {
        Optional<Book> getBook = bookRepository.findById(id);
        if(getBook.isPresent()){
            getBook.get().setIsborrowed(status);

            return bookRepository.save(getBook.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }
}
