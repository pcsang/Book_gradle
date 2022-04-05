package com.example.demoBook.service;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demoBook.book.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class BookServiceJooqTest {

    @Autowired
    DSLContext dsl;

    Book book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
            "Khoahoc", "Sach kham pha",
            LocalDate.of(2010, 04, 12),
            LocalDate.of(2015, 8,11));

    @Test
    void getBooks() {
        List<Book> books = dsl.selectFrom("book").fetchInto(Book.class);
        Assert.assertEquals(1, books.size());
    }

    @Test
    void addNewBook() {
        dsl.insertInto(table("book"))
                .set(field("id"),book.getId())
                .set(field("name"), book.getName())
                .set(field("author"), book.getAuthor())
                .set(field("category"), book.getCategory())
                .set(field("desciption"), book.getDesciption())
                .set(field("create_date"), book.getCreateDate())
                .set(field("update_date"), book.getUpdateDate())
                .execute();
        Book resultBook = dsl.selectFrom("book").where(field("id").eq(2)).fetchOneInto(Book.class);
        Assert.assertEquals(2, resultBook.getId());
        Assert.assertEquals(book.getName(), resultBook.getName());
    }

    @Test
    void getABookId() {
        Book resultBook = dsl.selectFrom("book").where(field("id").eq(0)).fetchOneInto(Book.class);
        Assert.assertEquals(0, resultBook.getId());
    }

    @Test
    void getABookIdTestNoFoundId() {
        Book resultBook = dsl.selectFrom("book").where(field("id").eq(1001)).fetchOneInto(Book.class);
        Assert.assertEquals(null, resultBook);
    }

    @Test
    void getBookAuthor_Category() {
        List<Book> books = dsl.selectFrom("book")
                .where(field("author").equal("pcsang"))
                .and(field("category").equal("Note"))
                .fetchInto(Book.class);
        Assert.assertEquals(1, books.size());
    }

    @Test
    void getBookAuthor_CategoryNotFount() {
        List<Book> books = dsl.selectFrom("book")
                .where(field("author").equal("pcsangb1706521"))
                .and(field("category").equal("Note"))
                .fetchInto(Book.class);
        Assert.assertEquals(0, books.size());
    }

    @Test
    void updateBook() {
        int execute = dsl.update(table("book")).set(field("name"), "10 van cau hoi vi sao?")
                .set(field("author"), "Pham Chi Sang").where(field("id").eq(2)).execute();
        Book resultBook = dsl.selectFrom("book").where(field("id").eq(2)).fetchOneInto(Book.class);
        Assert.assertEquals(1, execute);
        Assert.assertEquals("10 van cau hoi vi sao?", resultBook.getName());
        Assert.assertEquals("Pham Chi Sang", resultBook.getAuthor());
    }

    @Test
    void updateBookNotFoundId() {
        int execute = dsl.update(table("book")).set(field("name"), "10 van cau hoi vi sao?")
                .set(field("author"), "Pham Chi Sang").where(field("id").eq(1000)).execute();
        Assert.assertEquals(0, execute);
    }

    @Test
    void deleteBook() {
        int execute = dsl.delete(table("book")).where(field("id").eq(2)).execute();
        Assert.assertEquals(1, execute);
    }
}