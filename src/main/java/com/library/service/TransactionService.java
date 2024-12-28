package com.library.service;

import com.library.model.Transaction;
import com.library.request.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface TransactionService {

    ResponseEntity<?> borrow(TransactionRequest borrowRequest);

    ResponseEntity<?> returnBook(TransactionRequest returnRequest);

    Page<Transaction> getAll(Integer page, Integer size);

    ResponseEntity<?> getById(Long id);
}
