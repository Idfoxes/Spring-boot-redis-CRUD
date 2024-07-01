package com.skillbox.Spring_boot_redis_CRUD.repository;

import com.skillbox.Spring_boot_redis_CRUD.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndAuthor(String title, String author);
    List<Book> findByCategoryName(String categoryName);
}
