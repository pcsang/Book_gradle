package com.example.demoBook.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DefaultDSLContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoBook.book.Book;
import com.example.demoBook.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    private DefaultDSLContext dsl;
    private Table<? extends Record> Book;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
//        List<Book> books = dsl.select().from("book")
//                .fetchInto(Book.class);
//        List<Book> books = dsl.selectFrom(Book).fetchInto(Book.class);
//        return books;
    }

    public void addNewBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        boolean exists = bookRepository.existsById(id);
        String messenger = "Book with id"+id+"does not exists";
        if(!exists){
            throw new IllegalStateException(messenger);
        }
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book updateBook(int idBook, String name, String author) {
        boolean existBook = bookRepository.existsById(idBook);
        if(!existBook){
            throw new IllegalStateException("Book with id "+idBook+" does not exists");
        }

        Optional<Book> bookOption = bookRepository.findById(idBook);
        Book book = bookOption.get();
        if(name != null && name.length() > 0 && !Objects.equals(book.getName(), name)) {
            book.setName(name);
        }

        if(author != null && author.length() > 0 && !Objects.equals(book.getAuthor(), author)) {
            book.setAuthor(author);
        }
        return bookRepository.save(book);
    }

    public Optional<Book> getABookId(int idBook) {
        boolean exists = bookRepository.existsById(idBook);
        if(!exists) {
            throw new IllegalStateException("Book with id "+idBook+" does not exist.");
        }
        return bookRepository.findById(idBook);
    }

    public List<Book> getBookAuthor_Category(String author, String category) {
        return bookRepository.findBookAuthorAndCategory(author, category);
    }
}


