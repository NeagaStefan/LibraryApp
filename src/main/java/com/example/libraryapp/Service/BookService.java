package com.example.libraryapp.Service;

import com.example.libraryapp.Entity.Book;
import com.example.libraryapp.Entity.BookDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<BookDto> fetchAllBooks();

    List<BookDto> fetchBooksByTitle(String title);

    BookDto fetchBooksByISBN(String isbn);

    List<BookDto> fetchBooksByAuthorName(String authorName);

    Book save(BookDto bookDto);

    Book saveById(Long id, BookDto bookDto);

    void updateStatus(Long id, String status);

    List<BookDto> fetchBooksByStatus(String status);

    void deleteById(Long id);
}
