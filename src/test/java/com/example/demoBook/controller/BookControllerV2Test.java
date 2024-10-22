package com.example.demoBook.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demoBook.book.Book;
import com.example.demoBook.service.BookServiceJooqV2;
import com.example.demoBook.util.GivenData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class BookControllerV2Test {
    @MockBean
    private BookServiceJooqV2 bookServiceJooqV2;

    @Autowired
    private MockMvc mockMvc;

    private final String bookBody = GivenData.dataExpect("bookBody.json");
    private static final String URL_API = "/api/v2/books";
    private Book book;
    private List<Book> books = new ArrayList<>();

    public BookControllerV2Test() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
                "Khoahoc", "Sach kham pha",
                LocalDate.of(2010, 4, 12),
                LocalDate.of(2015, 8,11));
        books.add(book);
    }

    @Test
    public void getBooksSuccess200Test() throws Exception {
        when(bookServiceJooqV2.getAll()).thenReturn(books);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(URL_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getBookByIdSuccess200Test() throws Exception {
        when(bookServiceJooqV2.getBookById(any(Integer.class))).thenReturn(book);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API +"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getBookByParams200Test() throws Exception {
        when(bookServiceJooqV2.getBookById(any(Integer.class))).thenReturn(book);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL_API + "/by")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("author", "PhamSang"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void postBookSuccessTest() throws Exception {
        when(bookServiceJooqV2.addBook(any(Book.class))).thenReturn(book);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(URL_API)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bookBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteBookByIdSuccessTest() throws Exception {
        doNothing().when(bookServiceJooqV2).deleteBook(any(Integer.class));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL_API +"/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateBookByIdSuccessTest() throws Exception {
        when(bookServiceJooqV2.addBook(any(Book.class))).thenReturn(book);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(URL_API + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bookBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
