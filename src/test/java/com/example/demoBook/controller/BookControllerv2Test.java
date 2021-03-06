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
import com.example.demoBook.service.BookServiceJooq;
import com.example.demoBook.util.GivenData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class BookControllerv2Test {

    @MockBean
    BookServiceJooq bookServiceJooq;

    String bookBody = GivenData.dataExpect("bookBody.json");
    String urlApi = "http://localhost:9090/api/v1/books";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Book book;
    List<Book> books = new ArrayList<>();

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

    public BookControllerv2Test() throws IOException {
    }

    @Test
    public void getBooksSuccessTest() throws Exception {
        when(bookServiceJooq.getBooks()).thenReturn(books);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(urlApi)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getBookByIdSuccessTest() throws Exception {
        when(bookServiceJooq.getABookId(any(Integer.class))).thenReturn(book);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(urlApi+"/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void getBookAuthorAndCategorySuccessTest() throws Exception {
        when(bookServiceJooq.getBookAuthor_Category(any(String.class), any(String.class))).thenReturn(books);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(urlApi+"/test?author=pcsang&category=Note")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void registerNewBookSuccessTest() throws Exception {
        doNothing().when(bookServiceJooq).addNewBook(any(Book.class));
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(urlApi)
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bookBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteBookSuccess200Test() throws Exception {
        doNothing().when(bookServiceJooq).deleteBook(any(Integer.class));
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(urlApi+"/1")
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void updateBookSuccess200Test() throws Exception {
        when(bookServiceJooq.updateBook(any(Integer.class), any(String.class), any(String.class))).thenReturn(book);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(urlApi+"/1?authen=pcsang&category=Khoahoc")
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void countBookSuccess200Test() throws Exception {
        when(bookServiceJooq.getCountBook_Author(any(String.class))).thenReturn(1);
        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(urlApi + "/countBook")
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
