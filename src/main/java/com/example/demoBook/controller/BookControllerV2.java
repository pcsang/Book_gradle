package com.example.demoBook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demoBook.book.Book;
import com.example.demoBook.service.BookServiceJooqV2;

@RestController
@RequestMapping(path = "/api/v2/books")
public class BookControllerV2 {

    private final BookServiceJooqV2 bookServiceJooqV2;

    @Autowired
    public BookControllerV2(BookServiceJooqV2 bookServiceJooqV2) {
        this.bookServiceJooqV2 = bookServiceJooqV2;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookServiceJooqV2.getAll();
    }

    @GetMapping(path = "/{idBook}")
    public Book getBookById(@PathVariable("idBook") int idBook) {
        return bookServiceJooqV2.getBookById(idBook);
    }

    @GetMapping("/by")
    public List<Book> getBookAuthorAndCategory(@RequestParam(required = false) String author,
                                               @RequestParam(required = false) String category) {
        return bookServiceJooqV2.getBookAuthor_Category(author, category);
    }

    @PostMapping
    public Book registerNewBook(@RequestBody Book book) {
        return bookServiceJooqV2.addBook(book);
    }

    @DeleteMapping(path = "/{idBook}")
    public void deleteBook(@PathVariable("idBook") int idBook) {
        bookServiceJooqV2.deleteBook(idBook);
    }

    @PutMapping(path = "{idBook}")
    public Book updateBook(@PathVariable("idBook") int idBook,
                           @RequestBody(required = false) Book book) {
        return bookServiceJooqV2.updateBook(idBook, book);
    }

}
