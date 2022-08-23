package com.example.libraryapp.Service;

import com.example.libraryapp.Entity.Book;
import com.example.libraryapp.Entity.BookDto;
import com.example.libraryapp.Repository.BookRepo;
import com.example.libraryapp.Repository.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements  BookService {

    private  BookRepo bookRepo;
    private CustomerRepo customerRepo;
    private ModelMapper modelMapper;

    @Autowired
    public void BookServiceBeans(BookRepo bookRepo, ModelMapper modelMapper,CustomerRepo customerRepo) {
        this.bookRepo = bookRepo;
        this.modelMapper = modelMapper;
        this.customerRepo = customerRepo;
    }

    @Override
    public List<BookDto> fetchAllBooks() {

        return bookRepo.findAll().stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> fetchBooksByTitle(String title) {
        return bookRepo.findByTitle(title).stream().map(book ->modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookDto fetchBooksByISBN(String isbn) {
        Book bookResponse = bookRepo.findByISBN(isbn);
       return(convertToDto(bookResponse));

    }

    @Override
    public List<BookDto> fetchBooksByAuthorName(String authorName) {

        return bookRepo.findByAuthorNameIgnoreCase(authorName).stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public Book save(BookDto bookDto) {
        Book bookRequest = convertToEntity(bookDto);
        return bookRepo.save(bookRequest);
    }

    @Override
    public List<BookDto> fetchBooksByStatus(String status) {
        return  bookRepo.fetchBooksByStatusIgnoreCase(status).stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }

    @Override
    public Book saveById(Long id, BookDto bookDto) {
        Book bookRequest = convertToEntity(bookDto);
        Book bookDb = bookRepo.findById(id).get();
        if(Objects.nonNull(bookRequest.getAuthorName())&&!"".equalsIgnoreCase(bookRequest.getAuthorName())){
            bookDb.setAuthorName(bookRequest.getAuthorName());
        }
        if(Objects.nonNull(bookRequest.getTitle())&&!"".equalsIgnoreCase(bookRequest.getTitle())){
            bookDb.setTitle(bookRequest.getTitle());
        }
        if(Objects.nonNull(bookRequest.getStatus())&&!"".equalsIgnoreCase(bookRequest.getStatus())){
            bookDb.setStatus(bookRequest.getStatus());
        }
        if(Objects.nonNull(bookRequest.getISBN())){
            bookDb.setISBN(bookRequest.getISBN());
        }
        return bookRepo.save(bookDb);
    }

    @Override
    public void updateStatus(Long id, String status) {
        bookRepo.updateStatusById(id,status);
    }

    private BookDto convertToDto(Book book) {
        return (modelMapper.map(book, BookDto.class));
    }
    private Book convertToEntity(BookDto bookDto) {
        return (modelMapper.map(bookDto,Book.class));
    }





}
