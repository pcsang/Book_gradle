package com.example.demoBook.service;

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
        return jooqBookRepository.getBookById(idBook);
    }

    public List<Book> getBookAuthor_Category(String author, String category) {
        return jooqBookRepository.getBookBy_AuthorAndCategory(author, category);
    }

    public void addBook(Book book) {
        jooqBookRepository.save(book);
    }

    public void deleteBook(int idBook) {
        jooqBookRepository.deleteById(idBook);
    }

    public Book updateBook(int idBook, Book book) {
        return jooqBookRepository.updateBook(idBook, book);
    }
}
