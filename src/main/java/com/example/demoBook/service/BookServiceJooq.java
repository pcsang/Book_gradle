package com.example.demoBook.service;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.util.List;
import javax.transaction.Transactional;

import org.jooq.DSLContext;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoBook.book.Book;

@Service
public class BookServiceJooq {

    @Autowired
    DSLContext dslContext;

    Table BOOK = table("book");

    public List<Book> getBooks() {
        List<Book> books = dslContext.
                select()
                .from(BOOK)
                .fetchInto(Book.class);
        return books;
    }

    public void addNewBook(Book book) {
        dslContext.insertInto(BOOK)
                .set(field("id"),book.getId())
                .set(field("name"), book.getName())
                .set(field("author"), book.getAuthor())
                .set(field("category"), book.getCategory())
                .set(field("desciption"), book.getDesciption())
                .set(field("create_date"), book.getCreateDate())
                .set(field("update_date"), book.getUpdateDate())
                .execute();
    }


    public void deleteBook(int id) {
        String messenger = "Book with id "+id+" does not exists";
        int result = dslContext.delete(BOOK).where(field("id").eq(id)).execute();
        if(result==0){
            throw new IllegalStateException(messenger);
        }
    }

    @Transactional
    public Book updateBook(int idBook, String name, String author) {
        int execute = dslContext.update(table("book")).set(field("name"), name).set(field("author"), author)
                .where(field("id").eq(idBook))
                .execute();
        Book book;
        if (execute == 0) {
            throw new IllegalStateException("Book with id " + idBook + " does not exists");
        } else {
            book = (Book) dslContext.selectFrom(BOOK).where(field("id").eq(idBook)).fetchOneInto(Book.class);
            return book;
        }
    }

    public Book getABookId(int idBook) {
        Book book = (Book) dslContext.selectFrom(BOOK).where(field("id").eq(idBook)).fetchOneInto(Book.class);
        return book;
    }

    public List<Book> getBookAuthor_Category(String author, String category) {
        List<Book> books =  dslContext.select().from(BOOK)
                .where(field("author").equal(author))
                .and(field("category").equal(category))
                .fetchInto(Book.class);
        return books;
    }

}
