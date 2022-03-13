package com.example.demoBook.controllTest;

import com.example.demoBook.book.Book;
import com.example.demoBook.controller.BookController;
import com.example.demoBook.service.BookService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String urlAPI = "/api/v1/books";

    @Test
    public void getListBookTest() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(2, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010,05,12),
                LocalDate.of(2015,05,15));
        books.add(book);
        when(bookService.getBooks()).thenReturn(books);

        LocalDate dateCreate = LocalDate.of(2010,05,12);
        LocalDate dateUpdate = LocalDate.of(2015,05,15);

        this.mockMvc.perform(MockMvcRequestBuilders.get(urlAPI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("The war"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("phamsang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category").value("Khoahoc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desciption").value("quan su"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createDate").value(dateTimeFormatter.format(dateCreate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updateDate").value(dateTimeFormatter.format(dateUpdate)));
    }

    @Test
    public void getABookTest() throws Exception {
        Book book = new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010,05,12),
                LocalDate.of(2015,05,15));
        when(bookService.getABookId(1)).thenReturn(Optional.of(book));

        LocalDate dateCreate = LocalDate.of(2010,05,12);
        LocalDate dateUpdate = LocalDate.of(2015,05,15);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("The war"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("phamsang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Khoahoc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.desciption").value("quan su"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createDate").value(dateTimeFormatter.format(dateCreate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updateDate").value(dateTimeFormatter.format(dateUpdate)));
    }

    @Test
    public void getBookAuthorAndCategoryTest() throws Exception {
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createDate").value(dateTimeFormatter.format(dateCreate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updateDate").value(dateTimeFormatter.format(dateUpdate)));
    }

    @Test
    public void registerNewBookTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post(urlAPI)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"4\",\"author\":\"pcsang\", \"category\":\"lich su\", \"name\":\"sach giao khoa lich su 12\"," +
                                "\"desciption\":\"sach gianh cho hoc sinh THPT\",\"createDate\":\"2010-05-12\",\"updateDate\":\"2015-01-18\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void deleteBookTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateBookTest() throws Exception {
        LocalDate dateCreate = LocalDate.of(2010,05,12);
        LocalDate dateUpdate = LocalDate.of(2015,05,15);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/{id}",1)
                        .param("name", "Sach tham khao thi TOIEC")
                        .param("author", "pham chi sang")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"id\":\"4\",\"author\":\"pham chi sang\", \"category\":\"lich su\", \"name\":\"Sach tham khao thi TOIEC\"," +
                                "\"desciption\":\"sach gianh cho hoc sinh THPT\",\"createDate\":\"2010-05-12\",\"updateDate\":\"2015-01-18\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

