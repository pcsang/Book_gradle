package com.example.demoBook.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demoBook.book.Book;
import com.example.demoBook.service.BookService;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String urlApi = "/api/v1/books";

    @Test
    void getBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(2, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));
        books.add(book);
        when(bookService.getBooks()).thenReturn(books);

        LocalDate dateCreate = LocalDate.of(2010, 05, 12);
        LocalDate dateUpdate = LocalDate.of(2015, 05, 15);

        this.mockMvc.perform(MockMvcRequestBuilders.get(urlApi))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("The war"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("phamsang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Khoahoc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desciption").value("quan su"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createDate")
                        .value(dateTimeFormatter.format(dateCreate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updateDate")
                        .value(dateTimeFormatter.format(dateUpdate)));
    }

    @Test
    void getABook() throws Exception {
        Book book = new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010,05,12),
                LocalDate.of(2015,05,15));
//        when(bookService.getABookId(1)).thenReturn(Optional.of(book));
//
        LocalDate dateCreate = LocalDate.of(2010,05,12);
        LocalDate dateUpdate = LocalDate.of(2015,05,15);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("The war"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("phamsang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Khoahoc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.desciption").value("quan su"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createDate")
                        .value(dateTimeFormatter.format(dateCreate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updateDate")
                        .value(dateTimeFormatter.format(dateUpdate)));
    }

    @Test
    void getBookAuthorAndCategory() throws Exception {
        Book book = new Book(1, "The war", "PhamSang",
                "Khoahoc", "quan su",
                LocalDate.of(2010,05,12),
                LocalDate.of(2015,05,15));

        List<Book> books = new ArrayList<>();
        books.add(book);

        LocalDate dateCreate = LocalDate.of(2010,05,12);
        LocalDate dateUpdate = LocalDate.of(2015,05,15);

        when(bookService.getBookAuthor_Category("PhamSang", "Khoahoc")).thenReturn(books);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/test")
                        .param("author", "PhamSang")
                        .param("category", "Khoahoc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("The war"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("PhamSang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Khoahoc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desciption").value("quan su"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createDate")
                        .value(dateTimeFormatter.format(dateCreate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updateDate")
                        .value(dateTimeFormatter.format(dateUpdate)));
    }

    @Test
    void registerNewBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(urlApi)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"4\",\"author\":\"pcsang\", \"category\":\"lich su\""
                                +", \"name\":\"sach giao khoa lich su 12\""
                                +", \"desciption\":\"sach gianh cho hoc sinh THPT\",\"createDate\":\"2010-05-12\""
                                +", \"updateDate\":\"2015-01-18\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    void deleteBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateBook() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}",1)
                        .param("name", "Sach tham khao thi TOIEC")
                        .param("author", "pham chi sang")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"4\",\"author\":\"pham chi sang\", \"category\":\"lich su\""
                                +", \"name\":\"Sach tham khao thi TOIEC\""
                                +", \"desciption\":\"sach gianh cho hoc sinh THPT\",\"createDate\":\"2010-05-12\""
                                +", \"updateDate\":\"2015-01-18\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}