package com.nagarro.demo.service;

import com.nagarro.demo.model.Book;
import com.nagarro.demo.repo.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Book save(Book book) {
        return repo.save(book);
    }

    public List<Book> getAll() {
        return repo.findAll();
    }

    public Book getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Book update(Long id, Book book) {
        Book existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setPrice(book.getPrice());
            return repo.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
