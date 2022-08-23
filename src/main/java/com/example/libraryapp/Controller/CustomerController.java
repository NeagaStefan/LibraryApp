package com.example.libraryapp.Controller;

import com.example.libraryapp.Entity.Customer;
import com.example.libraryapp.Entity.CustomerDto;
import com.example.libraryapp.Service.BookService;
import com.example.libraryapp.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    private CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    //Get mappings
//Shows all customers
    @GetMapping("/customers")
    public List<CustomerDto> fetchAllCustomers(){
        return customerService.fetchAllCustomers();
    }

    //SHows customer by id

    @GetMapping ("/customers/")
    public CustomerDto fetchCustomerById(@RequestParam("id") Long id){
        return customerService.fetchCustomerById(id);
    }

    //SHow customer by last name

    @GetMapping("/customers/name")
    public List<CustomerDto> fetchCustomerByLastName(@RequestParam("lastName")String lastName){
        return customerService.fetchCustomerByLastName(lastName);
    }

    //Show customer by id
    @GetMapping("/customers/email")
    public CustomerDto fetchCustomerByEmail(@RequestParam("email")String email){
        return customerService.fetchCustomerByEmail(email);
    }

    //Post Mappings

    //Save a customer

    @PostMapping("/customers")
    public ResponseEntity<Customer> saveACustomer(@Valid @RequestBody CustomerDto customerDto){
        return ResponseEntity.ok().body(customerService.saveACustomer(customerDto));
    }


    //Updates a customer

    @PostMapping("/customers/")
    public ResponseEntity<Customer> updateACostumer(@Valid @RequestParam("id") Long id, @RequestBody CustomerDto customerDto){
        return ResponseEntity.ok().body(customerService.updateACostumer(id,customerDto));
    }

    // Puts the book taken to the customer

    @PutMapping("/customers/")
    public void updateBookTaken(@RequestParam("id") Long id, @RequestParam("title") String title){
        customerService.updateBookTaken(id, title);
    }

    //Updates the customer with 0 books taken and makes the book available

    @PutMapping("/customers/books/")
    public void updateBookReturned(@RequestParam("id") Long id, @RequestParam("title") String title){
        customerService.updateReturned(id, title);
    }

    //Delete mappings

    @DeleteMapping("/customers")
    public void deleteById(@RequestParam("id") Long id){
        customerService.deleteById(id);
    }

}
