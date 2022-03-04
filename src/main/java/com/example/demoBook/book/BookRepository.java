package com.example.demoBook.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.author LIKE ?1 AND b.category LIKE ?2")
    List<Book> findBookAuthorAndCategory(String author, String category);

}
