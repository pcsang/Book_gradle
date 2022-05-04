package com.example.demoBook.repository;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.demoBook.book.Book;

@Component
public class JooqBookRepository {

    private final String Table_Book = "book";
    private final String ID_FIELD = "id";
    private final String NAME_FIELD = "name";
    private final String AUTHOR_FIELD = "author";
    private final String CATEGORY_FIELD = "category";
    private final String DESCIPTION_FIELD = "desciption";
    private final String CREATE_FIELD = "create_date";
    private final String UPDATE_FIELD = "update_date";

    @Autowired
    DSLContext dsl;

    public List<Book> getAllBook() {
        List<Book> books = dsl.select()
                .from("book")
                .fetchInto(Book.class);
        return books;
    }

    public Book save(Book book) {
        dsl.insertInto(table("book"))
                .set(field(ID_FIELD), book.getId())
                .set(field(NAME_FIELD), book.getName())
                .set(field(AUTHOR_FIELD), book.getAuthor())
                .set(field(CATEGORY_FIELD), book.getCategory())
                .set(field(DESCIPTION_FIELD), book.getDesciption())
                .set(field(CREATE_FIELD), book.getCreateDate())
                .set(field(UPDATE_FIELD), book.getUpdateDate())
                .execute();
        return getBookById(book.getId());
    }

    public void deleteById(int id) {
        dsl.delete(table(Table_Book)).where(field(ID_FIELD).eq(id)).execute();
    }

    public Book getBookById(int id) {
        Book book = dsl.selectFrom(Table_Book).where(field(ID_FIELD).eq(id)).fetchOneInto(Book.class);
        return ObjectUtils.isEmpty(book)? null : book;
    }

    public Book updateBook(int id, Book book) {
        Book bookExist = getBookById(id);
        String messenger = "Book with id "+id+" does not exists";
        if(bookExist == null) {
            throw new IllegalStateException(messenger);
        }

        String name = ObjectUtils.isEmpty(book.getName())? bookExist.getName() : book.getName();
        String author = ObjectUtils.isEmpty(book.getAuthor())? bookExist.getAuthor() : book.getAuthor();
        String category = ObjectUtils.isEmpty(book.getCategory())? bookExist.getCategory() : book.getCategory();
        String description = ObjectUtils.isEmpty(book.getDesciption())
                ?bookExist.getDesciption() : book.getDesciption();
        LocalDate create = ObjectUtils.isEmpty(book.getCreateDate())? bookExist.getCreateDate() : book.getCreateDate();
        LocalDate update = ObjectUtils.isEmpty(book.getUpdateDate())? bookExist.getUpdateDate() : book.getUpdateDate();

        dsl.update(table(Table_Book))
                .set(field(NAME_FIELD), name)
                .set(field(AUTHOR_FIELD), author)
                .set(field(CATEGORY_FIELD), category)
                .set(field(DESCIPTION_FIELD), description)
                .set(field(CREATE_FIELD), create)
                .set(field(UPDATE_FIELD), update)
                .where(field(ID_FIELD).eq(book.getId()))
                .execute();

        return getBookById(id);
    }

    public List<Book> getBookBy_AuthorAndCategory(String author, String category) {
        List<Book> books =  dsl.select().from(Table_Book)
                .where(field(AUTHOR_FIELD).equal(author))
                .and(field(CATEGORY_FIELD).equal(category))
                .fetchInto(Book.class);
        return books;
    }
}
