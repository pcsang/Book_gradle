package com.example.demoBook.serviceTest;

import com.example.demoBook.book.Book;
import com.example.demoBook.repository.BookRepository;
import com.example.demoBook.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookUnderService;

    @BeforeEach
    void setUp() {
        bookUnderService = new BookService(bookRepository);
    }


    @Test
    void getBooks() {
        bookUnderService.getBooks();
        verify(bookRepository).findAll();
    }

    @Test
    void addNewBook() {
        Book book =  new Book(2, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010,05,12),
                LocalDate.of(2015,05,15));
        bookUnderService.addNewBook(book);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture());
        Book captrueBook = bookArgumentCaptor.getValue();
        assertThat(captrueBook).isEqualTo(book);
    }

    @Test
    void deleteBook() {
        assertThatThrownBy(()->bookUnderService.deleteBook(1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Book with id "+ 1 + " does not exists");

    }

    @Test
    void deleteBookTest() {
        given(bookRepository.existsById(1)).willReturn(true);
        bookUnderService.deleteBook(1);
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
        when(bookUnderService.updateBook(1,"The war 2", "pham chi sang"))
                .thenReturn(book);
        assertThat(bookRepository.save(book)).isEqualTo(bookUnderService.updateBook(1,"The war 2", "pham chi sang"));
    }

    @Test
    void getABookId() {
        given(bookRepository.existsById(1)).willReturn(true);
        when(bookUnderService.getABookId(1)).thenReturn(Optional.of(new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su",
                LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15))));
        assertThat(bookRepository.findById(1)).isEqualTo(bookUnderService.getABookId(1));
    }

    @Test
    void getBookByAuthor_Category() {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "The war", "phamsang",
                "Khoahoc", "quan su", LocalDate.of(2010, 05, 12),
                LocalDate.of(2015, 05, 15));
        books.add(book);

        when(bookUnderService.getBookAuthor_Category("phamsang", "Khoahoc"))
                .thenReturn(books);

        assertThat(bookRepository.findBookAuthorAndCategory("phamsang", "Khoahoc"))
                .isEqualTo(bookUnderService.getBookAuthor_Category("phamsang", "Khoahoc"));
    }

}
