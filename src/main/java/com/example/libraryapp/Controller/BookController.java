package com.example.libraryapp.Controller;

import com.example.libraryapp.Entity.Book;
import com.example.libraryapp.Entity.BookDto;
import com.example.libraryapp.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {
    private BookService bookService;

    @Autowired
    private BookController(BookService bookService){
        this.bookService = bookService;
    }
//Getmapptings



    @GetMapping("/")
    public String welcomeMessage(){
        return "Welcome to the Library of Alexandria";
    }

    //Shows all books

    @GetMapping("/books")
    public List<BookDto> fetchAllBooks(){
        return bookService.fetchAllBooks();
    }

    //Shows all books by title

    @GetMapping("/books/")
    public List<BookDto> fetchBooksByTitle(@RequestParam ("title") String title){
        return bookService.fetchBooksByTitle(title);
    }

    //Shows book by isbn

    @GetMapping("/books/isbn")
    public ResponseEntity<BookDto> fetchBooksByISBN(@RequestParam ("ISBN") String ISBN){
        return ResponseEntity.ok(bookService.fetchBooksByISBN(ISBN));
    }

    //Shows all books by author

    @GetMapping("/books/authors")
    public List<BookDto> fetchBooksByAuthor(@RequestParam ("authorName") String authorName){
        return bookService.fetchBooksByAuthorName(authorName);
    }

    //Shows all books by status

    @GetMapping("/books/status")
    private List<BookDto> fetchBooksByStatus(@RequestParam ("status") String status){
        return bookService.fetchBooksByStatus(status);
    }


    //Postmappings

    // Save a book

    @PostMapping("/books")
    public ResponseEntity<Book> saveABook(@Valid @RequestBody BookDto bookDto){
        return ResponseEntity.ok().body(bookService.save(bookDto));
    }

    //Update a book

    @PostMapping("/books/")
    public ResponseEntity<Book> updateABook(@Valid @RequestParam ("id") Long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok().body(bookService.saveById(id, bookDto));
    }

    //Updates the status of the book

    @PutMapping("/books/")
    public void updateStatus(@RequestParam ("id") Long id,@RequestParam ("status") String status) {
        bookService.updateStatus(id,status);
    }


    //Delete mappings
    @DeleteMapping("/books/")
    public void deleteById(@RequestParam ("id") Long id){
        bookService.deleteById(id);
    }

}
