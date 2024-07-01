package com.skillbox.Spring_boot_redis_CRUD.service;

import com.skillbox.Spring_boot_redis_CRUD.model.Book;
import com.skillbox.Spring_boot_redis_CRUD.model.Category;
import com.skillbox.Spring_boot_redis_CRUD.repository.BookRepository;
import com.skillbox.Spring_boot_redis_CRUD.repository.CategoryRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = "books", key = "#title + #author")
    public Optional<Book> findByTitleAndAuthor(String title, String author){
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Cacheable(value = "bookByCategory", key = "categoryName")
    public List<Book> findByCategoryName(String categoryName) {
        return bookRepository.findByCategoryName(categoryName);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public Book updateBook(Book book){
        return bookRepository.save(book);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    @CacheEvict(value = {"books", "booksByCategory"}, allEntries = true)
    public Book saveBook(Book book) {
        Optional<Category> category = categoryRepository.findByName(book.getCategory().getName());
        if (category.isPresent()) {
            book.setCategory(category.get());
        } else {
            categoryRepository.save(book.getCategory());
        }
        return bookRepository.save(book);
    }
}
