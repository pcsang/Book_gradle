package com.example.demoBook.service;

import static com.example.demoBook.messenger.Messenger.NOT_FOUNT_ID_OF_BOOK;

import java.util.List;

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
        String messenger = "ID Book" + idBook + "doesn't exits inside Book";
        Book book = getBookById(idBook);
        if(book == null){
            throw new IllegalStateException(messenger);
        }
        jooqBookRepository.deleteById(idBook);
    }

    public Book updateBook(int idBook, Book book) {
        return jooqBookRepository.updateBook(idBook, book);
    }
}
