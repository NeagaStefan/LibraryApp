package com.example.libraryapp.Controller;

import com.example.libraryapp.Entity.Book;
import com.example.libraryapp.Entity.BookDto;
import com.example.libraryapp.Service.BookService;
import com.example.libraryapp.Service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService customerService;
    private BookDto bookDto;
    private Book book;
    @MockBean
    private BookService bookService;
    @BeforeEach
    void setUp() {
        bookDto= new BookDto(2L, "Outsider","Stephen King","979-191-936-742","Available",0L);
        book = new Book(2L, "Outsider","Stephen King","979-191-936-742","Available",0L);    }

    @Test
    void fetchAllBooks() throws Exception {
        Mockito.when(bookService.fetchAllBooks()).thenReturn(new ArrayList<>(List.of(bookDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void fetchBooksByTitle() throws Exception{
        Mockito.when(bookService.fetchBooksByTitle(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(bookDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/books/").param("title", "Outsider")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void fetchBooksByISBN() throws Exception {
        Mockito.when(bookService.fetchBooksByISBN(ArgumentMatchers.anyString())).thenReturn(bookDto);
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/books/isbn").param("ISBN", "979-191-936-742")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        BookDto response = new ObjectMapper().readValue(mvcResultAsString,BookDto.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Outsider", response.getTitle());
        Assertions.assertEquals("Stephen King", response.getAuthorName());
    }


    @Test
    void fetchBooksByAuthor() throws Exception {
        Mockito.when(bookService.fetchBooksByAuthorName(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(bookDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/books/authors").param("authorName", "Stephen King")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());

    }
    @Test
    void fetchBooksByStatus() throws Exception{
        Mockito.when(bookService.fetchBooksByStatus(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>(List.of(bookDto)));
        String mvcResultAsString =mvc.perform(MockMvcRequestBuilders.get("/books/status").param("status", "Taken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List response = new ObjectMapper().readValue(mvcResultAsString,List.class);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void saveABook() throws Exception{
        Mockito.when(bookService.save(Mockito.any(BookDto.class))).thenReturn(book);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book)
                        .getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void updateABook() throws Exception {
        Mockito.when(bookService.saveById(ArgumentMatchers.any(),Mockito.any(BookDto.class))).thenReturn(book);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/books/").param("id","2")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book)
                                .getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result);
    }

    @Test
    void updateStatus() throws Exception{
        Mockito.doNothing().when(bookService).updateStatus(ArgumentMatchers.any(),ArgumentMatchers.anyString());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/books/")
                        .param("id","2").param("status","Taken")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception{
        Mockito.doNothing().when(bookService).deleteById(ArgumentMatchers.any());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/books/")
                        .param("id","2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}