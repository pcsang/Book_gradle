package com.example.demoBook.service;

import static com.example.demoBook.messenger.Messenger.NOT_FOUNT_ID_OF_BOOK;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;

import com.example.demoBook.book.Book;
import com.example.demoBook.repository.JooqBookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BookServiceJooqV2Test {
    private Book book;
    private List<Book> booksExpect = new ArrayList<>();
    @Mock
    JooqBookRepository jooqBookRepository;

    @InjectMocks
    BookServiceJooqV2 bookServiceJooqV2;

    @PostConstruct
    public void postConstruct() {
        bookServiceJooqV2.jooqBookRepository = jooqBookRepository;
    }

    @BeforeEach
    public void setUp() {
        book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
                "Khoahoc", "Sach kham pha",
                LocalDate.of(2010, 04, 12),
                LocalDate.of(2015, 8,11));
        booksExpect.add(book);
    }

    @Test
    public void getAllTest() {
        Mockito.when(jooqBookRepository.getAllBook()).thenReturn(booksExpect);
        List<Book> booksActual = bookServiceJooqV2.getAll();
        AssertionErrors.assertEquals("Wrong ok", booksActual, booksExpect);
    }

    @Test
    public void getAllSuccessTest() {
        Mockito.when(jooqBookRepository.getAllBook()).thenReturn(booksExpect);
        List<Book> booksActual = bookServiceJooqV2.getAll();
        Assert.assertEquals(booksActual, booksExpect);
    }

    @Test
    public void getBookByIdSuccessesTest() {
        Mockito.when(jooqBookRepository.getBookById(any(Integer.class))).thenReturn(book);
        Book bookExpect = bookServiceJooqV2.getBookById(1);
        Assert.assertEquals(bookExpect, book);
    }

    @Test
    public void getBookByIdFailWithIDNotFound() {

        Mockito.when(jooqBookRepository.getBookById(any(Integer.class))).thenReturn(null);
        Throwable responce = Assertions.assertThrows(IllegalStateException.class,
                ()->bookServiceJooqV2.getBookById(1));
        Assertions.assertEquals(String.format(NOT_FOUNT_ID_OF_BOOK, 1), responce.getMessage());
    }

    @Test
    public void getBookByCategoryAndAuthorSuccessTest() {
        Mockito.when(jooqBookRepository.getBookBy_AuthorAndCategory(any(String.class), any(String.class)))
                .thenReturn(booksExpect);
        List<Book> booksActual = bookServiceJooqV2.getBookAuthor_Category("pcsang", "Note");
        Assert.assertEquals(booksExpect, booksActual);
    }

    @Test
    public void getBookByCategoryAndAuthorSuccessWithNullTest() {
        Mockito.when(jooqBookRepository.getBookBy_AuthorAndCategory(any(String.class), any(String.class)))
                .thenReturn(null);
        List<Book> booksActual = bookServiceJooqV2.getBookAuthor_Category("pcsang", "Note");
        Assert.assertEquals(null, booksActual);
    }

    @Test
    public void addBookSuccessTest() {
        Mockito.when(jooqBookRepository.save(any(Book.class))).thenReturn(book);
        Book bookActual = bookServiceJooqV2.addBook(book);
        Assertions.assertEquals(bookActual, book);
    }


}
