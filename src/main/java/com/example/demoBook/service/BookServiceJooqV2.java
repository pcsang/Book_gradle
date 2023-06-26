package com.example.demoBook.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demoBook.book.Book;
import com.example.demoBook.exceptions.ExceptionInput;
import com.example.demoBook.messenger.Messenger;
import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.LDClient;

import com.example.demoBook.repository.JooqBookRepository;

@Service
public class BookServiceJooqV2 {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final JooqBookRepository jooqBookRepository;
    private final LDClient ldClient;

    @Autowired
    public BookServiceJooqV2(JooqBookRepository jooqBookRepository, @Qualifier("ldClient") LDClient ldClient) {
        this.jooqBookRepository = jooqBookRepository;
        this.ldClient = ldClient;
    }

    public List<Book> getAll() {
        LDUser user = new LDUser.Builder("user-test").build();
        boolean show = ldClient.boolVariation("get-book-all", user, false);
        if(Boolean.TRUE.equals(show)) {
            throw new IllegalStateException("API is not available");
        }
        return jooqBookRepository.getAllBook();
    }

    public Book getBookById(int idBook) {
        Book book = jooqBookRepository.getBookById(idBook);
        if(book == null) {
            throw new IllegalStateException(String.format(Messenger.NOT_FOUNT_ID_OF_BOOK, idBook));
        }
        return book;
    }

    public List<Book> getBookAuthor_Category(String author, String category) {
        return jooqBookRepository.getBookBy_AuthorAndCategory(author, category);
    }

    public Book addBook(Book book) {
        Book bookData = jooqBookRepository.getBookById(book.getId());
        if (!isEmpty(bookData)) {
            throw new ExceptionInput(Messenger.BOOK_EXITED_IN_DB);
        }

        updateDate(book);
        return jooqBookRepository.save(book);
    }

    public void deleteBook(int idBook) {
        Book book = jooqBookRepository.getBookById(idBook);
        if(isEmpty(book)){
            throw new ExceptionInput(String.format(Messenger.NOT_FOUNT_ID_OF_BOOK, idBook));
        }
        jooqBookRepository.deleteById(idBook);
    }

    public Book updateBook(int idBook, Book book) {
        if (idBook != book.getId()) {
            String msg = String.format(Messenger.ID_BOOK_IS_NOT_THE_SAME, idBook, book.getId());
            throw new ExceptionInput(msg);
        }
        Book bookData = jooqBookRepository.getBookById(idBook);
        if (isEmpty(bookData)) {
            throw new ExceptionInput(String.format(Messenger.NOT_FOUNT_ID_OF_BOOK, idBook));
        }
        updateDate(book);
        BeanUtils.copyProperties(book, bookData);
        return jooqBookRepository.updateBook(idBook, bookData);
    }

    public boolean isEmpty(Book book) {
        return book == null;
    }

    private static void updateDate(Book book) {
        LocalDate currentDay = LocalDate.now();
        currentDay.format(TIME_FORMATTER);
        book.setCreateDate(currentDay);
        book.setUpdateDate(currentDay);
    }
}
