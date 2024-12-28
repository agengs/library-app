package com.library.controller;

import com.library.model.Book;
import com.library.request.BookRequest;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("")
    public ResponseEntity<?> create (@RequestBody BookRequest bookRequest){
        try {
            return new ResponseEntity<>(bookService.save(bookRequest), HttpStatus.CREATED);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAll (
            @RequestParam(value = "status", required = false) boolean status,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ){

        try{
            Page<Book> books = bookService.getAll(status, page, size);
            Map<String, Object> result = new HashMap<>();
            result.put("message", "Get books list ");
            result.put("data", books.getContent());
            result.put("totalRecord", books.getTotalElements());
            result.put("totalPages", books.getTotalPages());
            result.put("status", HttpStatus.OK);

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllById (@PathVariable("id") Long id){
        try{
            return bookService.getById(id);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook (@PathVariable("id") Long id, @RequestBody BookRequest bookRequest){
        try {
            return new ResponseEntity<>(bookService.updateBook(id, bookRequest), HttpStatus.OK);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/status/{id}/{status}")
    public ResponseEntity<?> changeStatus (@PathVariable("id") Long id, @PathVariable("status") boolean status){
        try {
            return new ResponseEntity<>(bookService.changeStatus(id, status), HttpStatus.OK);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
