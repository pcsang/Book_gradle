package com.example.demoBook.controller;

import com.example.demoBook.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    void getBooks() {

    }

    @Test
    void getABook() {
    }

    @Test
    void getBookAuthorAndCategory() {
    }

    @Test
    void registerNewBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }
}