package com.example.demoBook.service;

import com.example.demoBook.book.Book;
import com.example.demoBook.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setup(){
        bookService = new BookService(bookRepository);
    }

    @Test
    void getBooksTest() {
        bookService.getBooks();
        verify(bookRepository).findAll();
    }

    @Test
    void addNewBook() {
        Book book =  new Book(2, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010,05,12),
                LocalDate.of(2015,05,15));
        bookService.addNewBook(book);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book captrueBook = bookArgumentCaptor.getValue();
        assertThat(captrueBook).isEqualTo(book);
    }

    @Test
    void deleteBook() {
        assertThatThrownBy(()->bookService.deleteBook(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Book with id "+ 1 + " does not exists");
    }

    @Test
    void deleteBookTest(){
        given(bookRepository.existsById(1)).willReturn(true);
        bookService.deleteBook(1);
        verify(bookRepository).deleteById(1);
    }

    @Test
    void updateBook() {
        //given
        Book book =  new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));
        given(bookRepository.findById(1)).willReturn(Optional.of(book));
        when(bookService.updateBook(1,"The war 2", "pham chi sang")).thenReturn(book);
        assertThat(bookRepository.save(book)).isEqualTo(bookService.updateBook(1,"The war 2", "pham chi sang"));
    }

    @Test
    void getBookId() {
        given(bookRepository.existsById(1)).willReturn(true);
        when(bookService.getABookId(1)).thenReturn(Optional.of(new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15))));
        assertThat(bookRepository.findById(1)).isEqualTo(bookService.getABookId(1));
    }

    @Test
    void getBookIdTest(){
        given(bookRepository.existsById(1)).willReturn(true);
        Book book = new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));
        when(bookService.getABookId(1)).thenReturn(Optional.of(book));
        assertThat(bookService.getABookId(1)).isEqualTo(Optional.of(book));
    }

    @Test
    void getBookAuthor_Category() {
        Book book1 = new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su", LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));

        Book book2 = new Book(2, "The war 2", "phamsang",
                "Khoahoc", "quan su ban moi", LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bookService.getBookAuthor_Category("phamsang", "Khoahoc")).thenReturn(books);

        assertThat(bookRepository.findBookAuthorAndCategory("phamsang", "Khoahoc"))
                .isEqualTo(bookService.getBookAuthor_Category("phamsang", "Khoahoc"));
    }

    @Test
    void getBookAuthor_CategoryTest(){
        Book book1 = new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su", LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));

        Book book2 = new Book(2, "The war 2", "phamsang",
                "Khoahoc", "quan su ban moi", LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bookService.getBookAuthor_Category("phamsang", "Khoahoc")).thenReturn(books);
        assertThat(bookService.getBookAuthor_Category("phamsang", "Khoahoc")).isEqualTo(books);

    }
}