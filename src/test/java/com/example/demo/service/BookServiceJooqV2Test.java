package com.example.demo.service;

import static com.example.demo.constants.Messenger.NOT_FOUNT_ID_OF_BOOK;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;

import com.example.demo.exception.ExceptionInput;
import com.example.demo.model.Book;
import com.example.demo.repository.JooqBookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BookServiceJooqV2Test {
    private Book book;
    private final List<Book> booksExpect = new ArrayList<>();
    @Mock
    private JooqBookRepository jooqBookRepository;

    @InjectMocks
    private BookServiceJooqV2 bookServiceJooqV2;

    @BeforeEach
    public void setUp() {
        book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
                "Khoahoc", "Sach kham pha",
                LocalDate.of(2010, 4, 12),
                LocalDate.of(2015, 8,11));
        booksExpect.add(book);
    }

    @Test
    public void getAllTest() {
        when(jooqBookRepository.getAllBook()).thenReturn(booksExpect);
        List<Book> booksActual = bookServiceJooqV2.getAll();
        AssertionErrors.assertEquals("Wrong ok", booksActual, booksExpect);
    }

    @Test
    public void getAllSuccessTest() {
        when(jooqBookRepository.getAllBook()).thenReturn(booksExpect);
        List<Book> booksActual = bookServiceJooqV2.getAll();
        assertEquals(booksActual, booksExpect);
    }

    @Test
    public void getBookByIdSuccessesTest() {
        when(jooqBookRepository.getBookById(any(Integer.class))).thenReturn(book);
        Book bookExpect = bookServiceJooqV2.getBookById(1);
        assertEquals(bookExpect, book);
    }

    @Test
    public void getBookByIdFailWithIDNotFound() {

        when(jooqBookRepository.getBookById(any(Integer.class))).thenReturn(null);
        Throwable response = assertThrows(IllegalStateException.class, ()->bookServiceJooqV2.getBookById(1));
        assertEquals(String.format(NOT_FOUNT_ID_OF_BOOK, 1), response.getMessage());
    }

    @Test
    public void getBookByCategoryAndAuthorSuccessTest() {
        when(jooqBookRepository.getBookBy_AuthorAndCategory(any(String.class), any(String.class)))
                .thenReturn(booksExpect);
        List<Book> booksActual = bookServiceJooqV2.getBookAuthorCategory("pcsang", "Note");
        assertEquals(booksExpect, booksActual);
    }

    @Test
    public void getBookByCategoryAndAuthorSuccessWithNullTest() {
        when(jooqBookRepository.getBookBy_AuthorAndCategory(any(String.class), any(String.class)))
                .thenReturn(null);
        List<Book> booksActual = bookServiceJooqV2.getBookAuthorCategory("pcsang", "Note");
        assertNull(booksActual);
    }

    @Test
    public void addBookSuccessTest() {
        when(jooqBookRepository.save(any(Book.class))).thenReturn(book);
        Book bookActual = bookServiceJooqV2.addBook(book);
        assertEquals(bookActual, book);
    }

    @Test
    public void deleteBookIdSuccessfully() {
        when(jooqBookRepository.getBookById(any(Integer.class))).thenReturn(book);
        doNothing().when(jooqBookRepository).deleteById(any(Integer.class));
        Assertions.assertDoesNotThrow(() -> bookServiceJooqV2.deleteBook(1));
    }

    @Test
    public void deleteBookIdFailWithIdNotFound() {
        Throwable exception = assertThrows(ExceptionInput.class, () -> bookServiceJooqV2.deleteBook(1));
        assertEquals(String.format(NOT_FOUNT_ID_OF_BOOK, 1), exception.getMessage());
    }

    @Test
    public void updateBookSuccess() {
        when(bookServiceJooqV2.updateBook(eq(2), any(Book.class))).thenReturn(book);
        var bookActual = bookServiceJooqV2.updateBook(2, book);
        Assertions.assertEquals(2, bookActual.getId());
    }
}
