package com.library.service;

import com.library.model.Customer;
import com.library.repository.CustomerRepository;
import com.library.request.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Customer save(CustomerRequest customerRequest) {
        Customer newBook = Customer.builder()
                .name(customerRequest.getName())
                .dob(customerRequest.getDob())
                .address(customerRequest.getAddress())
                .build();
        return customerRepository.save(newBook);
    }

    @Override
    public Page<Customer> getAll(Integer page, Integer size) {
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Customer> customerPage = customerRepository.findAll(pageRequest);


        return customerPage;
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer updateBook(Long id, CustomerRequest customerRequest) {
        Optional<Customer> getCustomer = customerRepository.findById(id);
        if(getCustomer.isPresent()){
            getCustomer.get().setName(customerRequest.getName());
            getCustomer.get().setDob(customerRequest.getDob());
            getCustomer.get().setAddress(customerRequest.getAddress());

            return customerRepository.save(getCustomer.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

}
