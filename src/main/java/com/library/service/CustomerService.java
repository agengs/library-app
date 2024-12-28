package com.library.service;

import com.library.model.Customer;
import com.library.request.CustomerRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CustomerService {

    Customer save(CustomerRequest customerRequest);

    Page<Customer> getAll(Integer page, Integer size);

    Optional<Customer> getById(Long id);

    Customer updateBook(Long id, CustomerRequest customerRequest);
}
