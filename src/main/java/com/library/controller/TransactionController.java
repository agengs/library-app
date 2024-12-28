package com.library.controller;

import com.library.model.Customer;
import com.library.model.Transaction;
import com.library.request.TransactionRequest;
import com.library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<?> getAll (
            @RequestParam(value = "status", required = false) boolean status,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ){

        try{
            Page<Transaction> transaction = transactionService.getAll(page, size);
            Map<String, Object> result = new HashMap<>();
            result.put("message", "Get books list ");
            result.put("data", transaction.getContent());
            result.put("totalRecord", transaction.getTotalElements());
            result.put("totalPages", transaction.getTotalPages());
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
    public ResponseEntity<?> getAllById(@PathVariable("id") Long id){
        try{
            return transactionService.getById(id);
        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBooks(@RequestBody TransactionRequest borrowRequest){
        try{
            return transactionService.borrow(borrowRequest);
        }catch (Exception e){
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBooks(@RequestBody TransactionRequest returnRequest){
        try{
            return transactionService.returnBook(returnRequest);
        }catch (Exception e){
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
