package com.example.demoBook.book;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
public class Book{

    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "author")
    private String author;
    @Column(name = "category")
    private String category;
    @Column(name = "desciption")
    private String desciption;

    @Column(name = "create_Date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    @Column(name = "update_Date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    public Book(int id, String name, String author, String category, String description,
                LocalDate createDate, LocalDate updateDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.desciption = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Book(String name, String author, String category, String description,
                LocalDate createDate, LocalDate updateDate) {
        this.name = name;
        this.author = author;
        this.category = category;
        this.desciption = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", author='" + author + '\''
                + ", category='" + category + '\''
                + ", desciption='" + desciption + '\''
                + ", createDate=" + createDate
                + ", updateDate=" + updateDate + '}';
    }
}
