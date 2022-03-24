package com.example.demoBook.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoBook.book.Book;
import com.example.demoBook.repository.BookRepository;

import static org.jooq.impl.DSL.table;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    DSLContext dslContext;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    public List<Book> getBooks() {
//        return bookRepository.findAll();
//    }

    Table BOOK = table("book");

    public List<Book> getBooks() {
        List<Book> books = dslContext.
                select()
                .from(BOOK)
                .fetchInto(Book.class);
        return books;
    }

    public void addNewBook(Book book) {
//        bookRepository.save(book);
        dslContext.insertInto(BOOK, BOOK.field("id"),BOOK.field("author")
        ,BOOK.field("category"))
                .values(book.getId(),book.getAuthor(),book.getCategory())
                .execute();
    }


    public void deleteBook(int id) {
//        boolean exists = bookRepository.existsById(id);
        String messenger = "Book with id"+id+"does not exists";
//        if(!exists){
//            throw new IllegalStateException(messenger);
//        }
//        bookRepository.deleteById(id);
        int result = dslContext.delete(BOOK).where("id="+id).execute();
        if(result==0){
            throw new IllegalStateException(messenger);
        }
    }

    @Transactional
    public Book updateBook(int idBook, String name, String author) {
//        boolean existBook = bookRepository.existsById(idBook);
//        if(!existBook){
//            throw new IllegalStateException("Book with id "+idBook+" does not exists");
//        }
//
//        Optional<Book> bookOption = bookRepository.findById(idBook);
//        Book book = bookOption.get();
//        if(name != null && name.length() > 0 && !Objects.equals(book.getName(), name)) {
//            book.setName(name);
//        }
//
//        if(author != null && author.length() > 0 && !Objects.equals(book.getAuthor(), author)) {
//            book.setAuthor(author);
//        }
//        return bookRepository.save(book);
        dslContext.update(BOOK).set(BOOK.field("name"), name).set(BOOK.field("author"), author)
                .where("id="+idBook).execute();
        Book book = (Book) dslContext.selectFrom(BOOK).where("id="+idBook).fetchOneInto(Book.class);
        return book;
    }

    public Book getABookId(int idBook) {
//        boolean exists = bookRepository.existsById(idBook);
//        if(!exists) {
//            throw new IllegalStateException("Book with id "+idBook+" does not exist.");
//        }
//        return bookRepository.findById(idBook);
        Book book = (Book) dslContext.selectFrom(BOOK).where("id="+idBook).fetchOneInto(Book.class);
        return book;
    }

    public List<Book> getBookAuthor_Category(String author, String category) {
//        return bookRepository.findBookAuthorAndCategory(author, category);
        List<Book> books =  dslContext.select().from(BOOK)
                .where("author LIKE "+author)
                .and("category LIKE "+category)
                .fetchInto(Book.class);
        return books;
    }
}


