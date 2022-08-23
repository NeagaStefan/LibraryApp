package com.example.libraryapp.Service;

import com.example.libraryapp.Entity.Customer;
import com.example.libraryapp.Entity.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> fetchAllCustomers();

    Customer saveACustomer(CustomerDto customerDto);

    CustomerDto fetchCustomerById(Long id);

    List<CustomerDto> fetchCustomerByLastName(String lastName);

    CustomerDto fetchCustomerByEmail(String email);

    Customer updateACostumer(Long id, CustomerDto customerDto);

    void deleteById(Long id);


    void updateBookTaken(Long id, String title);

    void updateReturned(Long id, String title);
}
