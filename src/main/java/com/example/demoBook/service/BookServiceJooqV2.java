package com.example.demoBook.service;

import static com.example.demoBook.messenger.Messenger.NOT_FOUNT_ID_OF_BOOK;

import java.util.List;

import com.example.demoBook.exceptions.ExceptionInput;
import org.springframework.stereotype.Service;

import com.example.demoBook.book.Book;
import com.example.demoBook.repository.JooqBookRepository;

@Service
public class BookServiceJooqV2 {
    JooqBookRepository jooqBookRepository;

    public BookServiceJooqV2(JooqBookRepository jooqBookRepository) {
        this.jooqBookRepository = jooqBookRepository;
    }

    public List<Book> getAll() {
        return jooqBookRepository.getAllBook();
    }

    public Book getBookById(int idBook) {
        Book book = jooqBookRepository.getBookById(idBook);
        if(book == null) {
            throw new IllegalStateException(String.format(NOT_FOUNT_ID_OF_BOOK, idBook));
        }
        return book;
    }

    public List<Book> getBookAuthor_Category(String author, String category) {
        return jooqBookRepository.getBookBy_AuthorAndCategory(author, category);
    }

    public Book addBook(Book book) {
        return jooqBookRepository.save(book);
    }

    public void deleteBook(int idBook) {
        Book book = jooqBookRepository.getBookById(idBook);
        if(isEmpty(book)){
            throw new ExceptionInput(String.format(NOT_FOUNT_ID_OF_BOOK, idBook));
        }
        jooqBookRepository.deleteById(idBook);
    }

    public Book updateBook(int idBook, Book book) {
        return jooqBookRepository.updateBook(idBook, book);
    }

    public boolean isEmpty(Book book) {
        return (book == null) ? true : false;
    }

}
