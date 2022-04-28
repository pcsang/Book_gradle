package com.example.demoBook.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demoBook.service.BookServiceJooqV2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demoBook.book.Book;
import com.example.demoBook.service.BookServiceJooq;
import com.example.demoBook.util.GivenData;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class BookControllerAPIV2 {
    @MockBean
    BookServiceJooqV2 bookServiceJooqV2;

    String bookBody = GivenData.dataExpect("bookBody.json");
    String urlApi = "http://localhost:9090/api/v2/books";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Book book;
    List<Book> books = new ArrayList<>();

    public BookControllerAPIV2() throws IOException {
    }

    @BeforeEach
    public void setUp() {
        book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
                "Khoahoc", "Sach kham pha",
                LocalDate.of(2010, 04, 12),
                LocalDate.of(2015, 8,11));
        books.add(book);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBooksSuccess200Test() throws Exception {
        Mockito.when(bookServiceJooqV2.getAll()).thenReturn(books);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(urlApi)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}
