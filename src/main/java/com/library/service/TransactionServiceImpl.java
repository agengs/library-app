package com.library.service;

import com.library.model.Book;
import com.library.model.Customer;
import com.library.model.Transaction;
import com.library.repository.BookRepository;
import com.library.repository.TransactionRepository;
import com.library.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<?> borrow(TransactionRequest borrowRequest) {
        Optional<Transaction> isCustomerStillBorrowing = transactionRepository.findByCustomerIdAndReturnDateIsNull(borrowRequest.getCustomerId());
        if (isCustomerStillBorrowing.isPresent()){
           throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer still have book that returned yet ");
        }
        Optional<Customer> getCustomer = customerService.getById(borrowRequest.getCustomerId());
        if (getCustomer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        try {
            Transaction newTransaction = new Transaction();
            newTransaction.setBorrowDate(borrowRequest.getDate());
            newTransaction.setDueDate(new Date(borrowRequest.getDate().getTime() + TimeUnit.DAYS.toMillis(7)));
            newTransaction.setCustomer(getCustomer.get());

            List<Book> borrowedBooks = new ArrayList<>();
            for (Long bookId : borrowRequest.getBookIdList()) {
                Optional<Book> getBook = bookRepository.findById(bookId);
                getBook.ifPresent(book -> {
                    book.setIsborrowed(true);
                    borrowedBooks.add(getBook.get());
                });
            }
            newTransaction.setBooks(borrowedBooks);
            transactionRepository.save(newTransaction);

            Map<String, Object> result = new HashMap<>();
            result.put("message", "The Books is borrowed successfully");
            result.put("status", HttpStatus.OK);

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> returnBook(TransactionRequest returnRequest) {
        Optional<Customer> getCustomer = customerService.getById(returnRequest.getCustomerId());
        if (getCustomer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        try {
            Optional<Transaction> getTransaction = transactionRepository.findByCustomerIdAndReturnDateIsNull(returnRequest.getCustomerId());
            if (getTransaction.isEmpty()){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer doesn't borrow any book.");
            }else{
                for (Long bookId: returnRequest.getBookIdList()){
                    getTransaction.get().getBooks().stream()
                            .filter(x -> x.getId().equals(bookId))
                            .findFirst().ifPresent(book -> book.setIsborrowed(false));
                }
                if (getTransaction.get().getBooks().stream().noneMatch(x -> x.isIsborrowed())){
                    getTransaction.get().setReturnDate(returnRequest.getDate());
                    getTransaction.get().setLate(getTransaction.get().getReturnDate().compareTo(getTransaction.get().getDueDate()) > 0);
                }
            }
            transactionRepository.save(getTransaction.get());

            Map<String, Object> result = new HashMap<>();
            result.put("message", "The Books is returned successfully");
            result.put("status", HttpStatus.OK);

            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch(Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("message", e.getMessage());
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Transaction> getAll(Integer page, Integer size) {
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Transaction> transactions = transactionRepository.findAll(pageRequest);


        return transactions;
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Map<String, Object> result = new HashMap<>();
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if(transaction == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        result.put("message", "Get transaction detail");
        result.put("transaction", transaction);
        result.put("bookList", transaction.getBooks());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
