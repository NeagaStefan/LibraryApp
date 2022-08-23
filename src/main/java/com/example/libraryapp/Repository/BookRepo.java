package com.example.libraryapp.Repository;

import com.example.libraryapp.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book,Long> {
    @Query(value = "select b from Book b where b.authorName like %:authorName%")
    List<Book> findByAuthorNameIgnoreCase(@Param("authorName") String authorName);

    @Query(value = "Select b from Book b where b.ISBN = ?1")
    Book findByISBN(String isbn);

    List<Book> findByTitle(String title);

    @Query(value ="select b from Book b where b.status = ?1")
    List<Book> fetchBooksByStatusIgnoreCase(String status);

    @Query(value = "select b from Book b order by b.bookId " )
    List<Book> findAll();

    @Transactional
    @Modifying
    @Query(value = "update Book b set b.status ='Taken',b.takenBy= :customerId where  b.title like %:title%")
    void updateStatusByTitle(@Param("customerId") Long customerId,@Param("title") String title);

    @Transactional
    @Modifying
    @Query(value = "update Book b set b.status ='Avaiable',b.takenBy= 0 where  b.title like %:title%")
    void updateStatusAvaByTitle(@Param("title") String title);

    @Transactional
    @Modifying
    @Query(value = "update Book b set b.status =:status where  b.bookId= :id")
    void updateStatusById(@Param("id") Long id,@Param("status") String status);

}
