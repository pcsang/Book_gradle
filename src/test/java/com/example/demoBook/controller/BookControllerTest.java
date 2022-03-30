package com.example.demoBook.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import com.example.demoBook.book.Book;
import com.example.demoBook.service.BookServiceJooq;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BookServiceJooq bookServiceJooq;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String urlApi = "/api/v1/books";

    Book book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
            "Khoahoc", "Sach kham pha",
            LocalDate.of(2010, 04, 12),
            LocalDate.of(2015, 8,11));

    @WithMockUser(value = "sang")
    @Test
    void getBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(book);
        given(bookServiceJooq.getBooks()).willReturn(books);
        when(bookServiceJooq.getBooks()).thenReturn(books);
        this.mockMvc.perform(MockMvcRequestBuilders.get(urlApi)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(book.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(book.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desciption").value(book.getDesciption()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createDate")
                        .value(dateTimeFormatter.format(book.getCreateDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updateDate")
                        .value(dateTimeFormatter.format(book.getUpdateDate())));
    }

    @WithMockUser(value = "sang")
    @Test
    void getABook() throws Exception {
        given(bookServiceJooq.getABookId(2)).willReturn(book);
        when(bookServiceJooq.getABookId(2)).thenReturn(book);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}",2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(book.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(book.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.desciption").value(book.getDesciption()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createDate")
                        .value(dateTimeFormatter.format(book.getCreateDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updateDate")
                        .value(dateTimeFormatter.format(book.getUpdateDate())));
    }

    @WithMockUser(value = "sang")
    @Test
    void getBookAuthorAndCategory() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookServiceJooq.getBookAuthor_Category("PhamSang", "Khoahoc")).thenReturn(books);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/test")
                        .param("author", "PhamSang")
                        .param("category", "Khoahoc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(book.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(book.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value(book.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desciption").value(book.getDesciption()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createDate")
                        .value(dateTimeFormatter.format(book.getCreateDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updateDate")
                        .value(dateTimeFormatter.format(book.getUpdateDate())));
    }

    @Test
    void registerNewBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(urlApi)
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"10\",\"author\":\"pcsang\", \"category\":\"lich su\""
                                +", \"name\":\"sach giao khoa lich su 12\""
                                +", \"desciption\":\"sach gianh cho hoc sinh THPT\",\"createDate\":\"2010-05-12\""
                                +", \"updateDate\":\"2015-01-18\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    void updateBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}",10)
                        .param("name", "Sach tham khao thi TOIEC")
                        .param("author", "pham chi sang")
                        .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"10\",\"author\":\"pham chi sang\", \"category\":\"lich su\""
                                +", \"name\":\"Sach tham khao thi TOIEC\""
                                +", \"desciption\":\"sach gianh cho hoc sinh THPT\",\"createDate\":\"2010-05-12\""
                                +", \"updateDate\":\"2015-01-18\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateBookTest()throws Exception{
        when(bookServiceJooq.updateBook(2, book.getName(), book.getAuthor())).thenReturn(book);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}",2)
                        .param("name", book.getName())
                        .param("author", book.getAuthor())
                .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser(value = "sang", roles = {"ADMIN"})
    @Test
    void deleteBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/{id}", 10)
                .with(SecurityMockMvcRequestPostProcessors.user("sang").roles("USER", "ADMIN"))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}