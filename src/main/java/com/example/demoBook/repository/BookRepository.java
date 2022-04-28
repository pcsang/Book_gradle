//package com.example.demoBook.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.demoBook.book.Book;
//
//@Repository
//public interface BookRepository extends JpaRepository<Book, Integer> {
//    @Query("SELECT b FROM Book b WHERE b.author LIKE ?1 AND b.category LIKE ?2")
//    List<Book> findBookAuthorAndCategory(String author, String category);
//}