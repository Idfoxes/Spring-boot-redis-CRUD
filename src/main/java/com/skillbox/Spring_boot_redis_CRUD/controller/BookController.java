package com.skillbox.Spring_boot_redis_CRUD.controller;

import com.skillbox.Spring_boot_redis_CRUD.model.Book;
import com.skillbox.Spring_boot_redis_CRUD.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{title}{author}")
    public ResponseEntity<Book> getBookByTitleAndAuthor(@PathVariable String title, @PathVariable String author){
        Optional<Book> book = bookService.findByTitleAndAuthor(title, author);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryName}")
    public List<Book> getBooksByCategoryName(@PathVariable String categoryName){
        return bookService.findByCategoryName(categoryName);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> bookOptional = bookService.findByTitleAndAuthor(bookDetails.getTitle(), bookDetails.getAuthor());
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setCategory(bookDetails.getCategory());
            return ResponseEntity.ok(bookService.updateBook(book));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
