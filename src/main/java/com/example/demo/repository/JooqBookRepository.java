package com.example.demo.repository;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.example.demo.model.Book;

@Component
public class JooqBookRepository {

    private static final String TABLE_BOOK = "book";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String AUTHOR_FIELD = "author";
    private static final String CATEGORY_FIELD = "category";
    private static final String DESCRIPTION_FIELD = "desciption";
    private static final String CREATE_FIELD = "create_date";
    private static final String UPDATE_FIELD = "update_date";

    @Autowired
    DSLContext dsl;

    public List<Book> getAllBook() {
        return dsl.select()
                .from("book")
                .fetchInto(Book.class);
    }

    public Book save(Book book) {
        dsl.insertInto(table("book"))
                .set(field(ID_FIELD), book.getId())
                .set(field(NAME_FIELD), book.getName())
                .set(field(AUTHOR_FIELD), book.getAuthor())
                .set(field(CATEGORY_FIELD), book.getCategory())
                .set(field(DESCRIPTION_FIELD), book.getDesciption())
                .set(field(CREATE_FIELD), book.getCreateDate())
                .set(field(UPDATE_FIELD), book.getUpdateDate())
                .execute();
        return getBookById(book.getId());
    }

    public void deleteById(int id) {
        dsl.delete(table(TABLE_BOOK)).where(field(ID_FIELD).eq(id)).execute();
    }

    public Book getBookById(int id) {
        Book book = dsl.selectFrom(TABLE_BOOK).where(field(ID_FIELD).eq(id)).fetchOneInto(Book.class);
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

        dsl.update(table(TABLE_BOOK))
                .set(field(NAME_FIELD), name)
                .set(field(AUTHOR_FIELD), author)
                .set(field(CATEGORY_FIELD), category)
                .set(field(DESCRIPTION_FIELD), description)
                .set(field(CREATE_FIELD), create)
                .set(field(UPDATE_FIELD), update)
                .where(field(ID_FIELD).eq(book.getId()))
                .execute();

        return getBookById(id);
    }

    public List<Book> getBookBy_AuthorAndCategory(String author, String category) {
        List<Condition> conditions = addCondition(author, category);
        return dsl.select().from(TABLE_BOOK)
                .where(conditions)
                .fetchInto(Book.class);
    }

    private static List<Condition> addCondition(String author, String category) {
        List<Condition> conditions = new ArrayList<>();
        if (!ObjectUtils.isEmpty(author)) {
            conditions.add(field(AUTHOR_FIELD).eq(author));
        }
        if (!ObjectUtils.isEmpty(category)) {
            conditions.add(field(CATEGORY_FIELD).eq(category));
        }
        return conditions;
    }
}
