package com.example.libraryapp.Repository;

import com.example.libraryapp.Entity.Customer;
import com.example.libraryapp.Entity.CustomerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    @Query(value = "select c from Customer c where c.customerId = :id")
    Customer findByCustomerId(@Param("id") Long id);
    @Query(value=" select c from Customer c where c.lastName = :lastName")
    List<CustomerDto> findAllByLastName(@Param("lastName") String lastName);

    @Query(value= "select c from Customer c where c.email = :email")
    CustomerDto findByEmail(@Param("email") String email);


    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.bookTaken =:title where  c.customerId =:id")
    void updateBookTaken(@Param("id") Long id,@Param("title") String title);

    @Transactional
    @Modifying
    @Query(value = "update Customer c set c.bookTaken = 'None' where  c.customerId =:id")
    void updateBookReturned(@Param("id") Long id);
}
