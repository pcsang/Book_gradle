package com.example.demoBook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoBook.book.Book;
import com.example.demoBook.service.BookService;
import com.example.demoBook.service.BookServiceJooq;

@RestController
@RequestMapping(path = "/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final BookServiceJooq bookServiceJooq;

    @Autowired
    public BookController(BookService bookService, BookServiceJooq bookServiceJooq) {
        this.bookService = bookService;
        this.bookServiceJooq = bookServiceJooq;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookServiceJooq.getBooks();
    }


    @GetMapping(path = "/{idBook}")
    public Book getBookById(@PathVariable("idBook") int idBook) {
        return bookServiceJooq.getABookId(idBook);
    }

    @GetMapping(path = "/test")
    public List<Book> getBookAuthorAndCategory(@RequestParam(required = false) String author,
                                               @RequestParam(required = false) String category) {
        return bookServiceJooq.getBookAuthor_Category(author, category);
    }

    @PostMapping
    public void registerNewBook(@RequestBody Book book) {
        bookServiceJooq.addNewBook(book);
    }

    @DeleteMapping(path = "/{idBook}")
    public void deleteBook(@PathVariable("idBook") int idBook) {
        bookServiceJooq.deleteBook(idBook);
    }

    @PutMapping(path = "{idBook}")
    public Book updateBook(@PathVariable("idBook") int idBook,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String author) {
        return bookServiceJooq.updateBook(idBook, name, author);
    }

    @GetMapping(path = "/countBook")
    public String getCountBookByAuthor(@RequestParam(required = false) String author) {
        int count = bookServiceJooq.getCountBook_Author(author);
        String json = "{"
                + "\"Author\":\""+author+"\""
                + ",\"BookCount\":\""+count+"\"}";
        return json;
    }
}


