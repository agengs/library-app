package com.library.repository;

import com.library.model.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByCustomerIdAndReturnDateIsNull(Long customerId);

    @Query("SELECT t FROM Transaction t JOIN t.books b WHERE b.id = :bookId ORDER BY t.id DESC")
    List<Transaction> findByBookIdInList(@Param("bookId") Long id);
}
