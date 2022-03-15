package com.example.demoBook.bookTest;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.example.demoBook.book.Book;

class BookTest {
    Book book =  new Book(2, "The war", "phamsang",
            "Khoahoc", "quan su",
            LocalDate.of(2010,05,12),
            LocalDate.of(2015,05,15));

    @Test
    void getId() {
        Assert.assertEquals(book.getId(), 2);
    }

    @Test
    void setId() {
        book.setId(3);
        Assert.assertEquals(book.getId(), 3);
    }

    @Test
    void getName() {
        Assert.assertEquals(book.getName(), "The war");
    }

    @Test
    void setName() {
        String nameNew = "The war 2";
        book.setName(nameNew);
        Assert.assertEquals(book.getName(), nameNew);
    }

    @Test
    void getAuthor() {
        Assert.assertEquals(book.getAuthor(), "phamsang");
    }

    @Test
    void setAuthor() {
        String authorNew = "Pham Chi Sang";
        book.setAuthor(authorNew);
        Assert.assertEquals(book.getAuthor(), authorNew);
    }

    @Test
    void getCategory() {
        Assert.assertEquals(book.getCategory(), "Khoahoc");
    }

    @Test
    void setCategory() {
        String categoryNew = "KHoa hoc the gioi";
        book.setCategory(categoryNew);
        Assert.assertEquals(book.getCategory(), categoryNew);
    }

    @Test
    void getDesciption() {
        Assert.assertEquals(book.getDesciption(), "quan su");
    }

    @Test
    void setDesciption() {
        String decNew = "Sach danh cho nghien cuu";
        book.setDesciption(decNew);
        Assert.assertEquals(book.getDesciption(), decNew);
    }

    @Test
    void getCreateDate() {
        LocalDate date = LocalDate.of(2010, 05, 12);
        Assert.assertEquals(date, book.getCreateDate());
    }

    @Test
    void setCreateDate() {
        LocalDate dateNew = LocalDate.of(2011, 01,02);
        book.setCreateDate(dateNew);
        Assert.assertEquals(dateNew, book.getCreateDate());
    }

    @Test
    void getUpdateDate() {
        LocalDate date = LocalDate.of(2015, 05,15);
        Assert.assertEquals(date, book.getUpdateDate());
    }

    @Test
    void setUpdateDate() {
        LocalDate dateNew = LocalDate.of(2016, 01, 01);
        book.setUpdateDate(dateNew);
        Assert.assertEquals(dateNew, book.getUpdateDate());
    }

    @Test
    void toStringTest() {
        String bookString = "Book{"
                + "id=" + 2
                + ", name='" + "The war" + '\''
                + ", author='" + "phamsang" + '\''
                + ", category='" + "Khoahoc" + '\''
                + ", desciption='" + "quan su" + '\''
                + ", createDate=" + "2010-05-12"
                + ", updateDate=" + "2015-05-15" + '}';
        Assert.assertEquals(bookString, book.toString());
    }

    @Test
    void bookTestNoId() {
        Book book1 = new Book("Sach thi TOEIC", "PCsang", "On thi", "danh cho nhung ai cos nhu cau thi TOEIC",
                LocalDate.of(2010,10,15),LocalDate.of(2019,10,10));
        Book book2 = new Book(1,"Sach thi TOEIC", "PCsang", "On thi", "danh cho nhung ai cos nhu cau thi TOEIC",
                LocalDate.of(2010,10,15),LocalDate.of(2019,10,10));

        Assert.assertNotEquals(book1, book2);
    }

}
