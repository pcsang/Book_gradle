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
import com.example.demoBook.service.BookServiceJooqV2;

@RestController
@RequestMapping(path = "/api/v2/books")
public class BookControllerV2 {

    private final BookServiceJooqV2 bookServiceJooqV2;

    /**
     * Constructor of BookController.
     *
     * @param bookServiceJooqV2 bookServiceJooqV2 is the service provider
     */
    @Autowired
    public BookControllerV2(BookServiceJooqV2 bookServiceJooqV2) {
        this.bookServiceJooqV2 = bookServiceJooqV2;
    }

    /**
     * Get books from DB.
     *
     * @return List of book
     */
    @GetMapping
    public List<Book> getBooks() {
        return bookServiceJooqV2.getAll();
    }

    /**
     * Get a book by Id.
     *
     * @param idBook id is defied for each book
     * @return a book
     */
    @GetMapping(path = "/{idBook}")
    public Book getBookById(@PathVariable("idBook") int idBook) {
        return bookServiceJooqV2.getBookById(idBook);
    }

    /**
     * Get books by author and category.
     *
     * @param author    author
     * @param category  category
     * @return list of book are filtered following these
     */
    @GetMapping("/by")
    public List<Book> getBookAuthorAndCategory(@RequestParam(required = false) String author,
                                               @RequestParam(required = false) String category) {
        return bookServiceJooqV2.getBookAuthor_Category(author, category);
    }

    /**
     * POST created a book new.
     *
     * @param book book is a request body
     * @return book is saved into DB
     */
    @PostMapping
    public Book registerNewBook(@RequestBody Book book) {
        return bookServiceJooqV2.addBook(book);
    }

    /**
     * DELETE remove a book by id.
     *
     * @param idBook id is defied for each the book
     */
    @DeleteMapping(path = "/{idBook}")
    public void deleteBook(@PathVariable("idBook") int idBook) {
        bookServiceJooqV2.deleteBook(idBook);
    }

    /**
     * PUT update a book exiting in DB.
     *
     * @param idBook id iss defied for each the book
     * @param book   book is a request body
     * @return the book is updated into DB
     */
    @PutMapping(path = "{idBook}")
    public Book updateBook(@PathVariable("idBook") int idBook,
                           @RequestBody(required = false) Book book) {
        return bookServiceJooqV2.updateBook(idBook, book);
    }

}
