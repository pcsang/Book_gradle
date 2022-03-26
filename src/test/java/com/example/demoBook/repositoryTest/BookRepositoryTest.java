//package com.example.demoBook.repositoryTest;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import com.example.demoBook.book.Book;
//import com.example.demoBook.repository.BookRepository;
//
//@DataJpaTest
//class BookRepositoryTest {
//
//    @Autowired
//    private BookRepository bookReposTest;
//
//    @Test
//    void findBookAuthorAndCategory() {
//        Book book = new Book(2, "10000 cau hoi vi sao?", "PhamSang",
//                "Khoahoc", "Sach kham pha",
//                LocalDate.of(2010, 04, 12),
//                LocalDate.of(2015, 8,11));
//
//        List<Book> books = bookReposTest.findBookAuthorAndCategory("PhamSang", "Khoahoc");
//        assertThat(books).asList();
//    }
//
//    @Test
//    void findBookAuthorAndCategoryNot() {
//        Book book =  new Book(2, "The war", "phamsang",
//                "Khoahoc", "quan su",
//                LocalDate.of(2010,05,12), LocalDate.of(2015,05,15));
//
//        bookReposTest.save(book);
//
//        List<Book> books = bookReposTest.findBookAuthorAndCategory("phamsang", "sangABc");
//        assertThat(books).isNotEqualTo(book);
//    }
//}