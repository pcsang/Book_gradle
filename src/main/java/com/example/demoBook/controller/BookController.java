package com.example.demoBook.controller;

import java.util.List;

import com.example.demoBook.service.BookServiceJooq;
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
//        return bookService.getBooks();
        return bookServiceJooq.getBooks();
    }


    @GetMapping(path = "/{idBook}")
    public Book getBookById(@PathVariable("idBook") int idBook) {
//        return bookService.getABookId(idBook);
        return bookServiceJooq.getABookId(idBook);
    }

    @GetMapping(path = "/test")
    public List<Book> getBookAuthorAndCategory(@RequestParam(required = false) String author,
                                               @RequestParam(required = false) String category) {
//        return bookService.getBookAuthor_Category(author, category);
        return bookServiceJooq.getBookAuthor_Category(author, category);
    }

    @PostMapping
    public void registerNewBook(@RequestBody Book book) {
//        bookService.addNewBook(book);
        bookServiceJooq.addNewBook(book);
    }

    @DeleteMapping(path = "{idBook}")
    public void deleteBook(@PathVariable("idBook") int idBook) {
//        bookService.deleteBook(idBook);
        bookServiceJooq.deleteBook(idBook);
    }

    @PutMapping(path = "{idBook}")
    public Book updateBook(@PathVariable("idBook") int idBook,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String author) {
//        return bookService.updateBook(idBook, name, author);
        return bookServiceJooq.updateBook(idBook, name, author);

    }
}


