package com.example.libraryapp.Service;

import com.example.libraryapp.Entity.BookDto;
import com.example.libraryapp.Entity.Customer;
import com.example.libraryapp.Entity.CustomerDto;
import com.example.libraryapp.Repository.BookRepo;
import com.example.libraryapp.Repository.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepo customerRepo;
    private final ModelMapper modelMapper;
    private  final BookRepo bookRepo;

    @Autowired
    private CustomerServiceImpl(CustomerRepo customerRepo, ModelMapper modelMapper, BookRepo bookRepo){
        this.customerRepo = customerRepo;
        this.modelMapper = modelMapper;
        this.bookRepo = bookRepo;
    }
    @Override
    public List<CustomerDto> fetchAllCustomers() {
        return customerRepo.findAll().stream().map(customer -> modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
    }

    @Override
    public Customer saveACustomer(CustomerDto customerDto) {
        Customer customerRequest = convertToEntity(customerDto);
        return customerRepo.save(customerRequest);
    }

    @Override
    public CustomerDto fetchCustomerById(Long id) {
        return (convertToDto(customerRepo.findByCustomerId(id)));
    }

    @Override
    public List<CustomerDto> fetchCustomerByLastName(String lastName) {
        return customerRepo.findAllByLastName(lastName).stream().map(customer -> modelMapper.map(customer,CustomerDto.class)).collect(Collectors.toList());
    }

    @Override
    public CustomerDto fetchCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    @Override
    public Customer updateACostumer(Long id, CustomerDto customerDto) {
        Customer customerDb = customerRepo.findById(id).get();
        Customer customerRequest = convertToEntity(customerDto);
        if(Objects.nonNull(customerRequest.getFirstName())&&!"".equalsIgnoreCase(customerRequest.getFirstName())){
            customerDb.setFirstName(customerRequest.getFirstName());
        }
        if(Objects.nonNull(customerRequest.getLastName())&&!"".equalsIgnoreCase(customerRequest.getLastName())){
            customerDb.setLastName(customerRequest.getLastName());
        }
        if(Objects.nonNull(customerRequest.getEmail())&&!"".equalsIgnoreCase(customerRequest.getEmail())){
            customerDb.setEmail(customerRequest.getEmail());
        }
//        if(Objects.nonNull(customerRequest.getBookTaken())&&!"".equalsIgnoreCase(customerRequest.getBookTaken())){
//            customerDb.setBookTaken(customerRequest.getBookTaken());
//        }
        return customerRepo.save(customerDb);
    }

    @Override
    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    @Override
    public void updateBookTaken(Long id, String title) {
        customerRepo.updateBookTaken(id,title);
        bookRepo.updateStatusByTitle(id, title);
    }

    @Override
    public void updateReturned(Long id, String title) {
        customerRepo.updateBookReturned(id);
        bookRepo.updateStatusAvaByTitle( title);

    }


    private CustomerDto convertToDto(Customer customer) {
        return (modelMapper.map(customer, CustomerDto.class));
    }
    private Customer convertToEntity(CustomerDto customerDto) {
        return (modelMapper.map(customerDto,Customer.class));
    }
}
