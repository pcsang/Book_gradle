package com.example.demoBook.controller;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demoBook.book.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demoBook.service.BookServiceJooqV2;
import com.example.demoBook.util.GivenData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class BookControllerV2Test {

    @MockBean
    BookServiceJooqV2 bookServiceJooqV2;

    String bookBody = GivenData.dataExpect("bookBody.json");
    String urlApi = "http://localhost:9090/api/v1/books";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private MockMvc mockMvc;

    public BookControllerV2Test() throws IOException {
    }

    @Test
    public void getBooksSuccessTest() throws Exception {
        Book book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
                "Khoahoc", "Sach kham pha",
                LocalDate.of(2010, 04, 12),
                LocalDate.of(2015, 8,11));
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookServiceJooqV2.getAll()).thenReturn(books);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(urlApi)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}