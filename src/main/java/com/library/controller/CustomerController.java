package com.library.controller;

import com.library.model.Customer;
import com.library.request.CustomerRequest;
import com.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("")
    public ResponseEntity<?> create (@RequestBody CustomerRequest customerRequest){
        try {
            return new ResponseEntity<>(customerService.save(customerRequest), HttpStatus.CREATED);
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
            Page<Customer> books = customerService.getAll(page, size);
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
            Optional<Customer> book = customerService.getById(id);
            if(book.isEmpty()) return new ResponseEntity<>(book, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(book.get(), HttpStatus.FOUND);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook (@PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest){
        try {
            return new ResponseEntity<>(customerService.updateBook(id, customerRequest), HttpStatus.OK);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
