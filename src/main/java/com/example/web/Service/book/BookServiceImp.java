package com.example.web.Service.book;

import com.example.web.Service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.web.data.Entity.Book;
import com.example.web.Repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Book> getAllBook() {
		return bookRepository.findAll();
	}

	@Override
	public void saveBook(Book book) {
		bookRepository.save(book);
	}

	@Override
	public Book getBookById(int id) {
		Optional<Book> optional = bookRepository.findById(id);
		Book book = null;
		if (optional.isPresent()) {
			book = optional.get();
		} else {
			throw new RuntimeException("Book not found with ID: " + id);
		}
		return book;
	}

	@Override
	public void deleteBookById(int id) {
		bookRepository.deleteById(id);
	}

	@Override
	public void updateBook(int id, Book book) {
		Optional<Book> optional = bookRepository.findById(id);
		Book bookInDB = null;
		if (optional.isPresent()) {
			bookInDB = optional.get();
			bookInDB.setNamebook(book.getNamebook());
			bookInDB.setAuthor(book.getAuthor());
			bookInDB.setTitle(book.getTitle());
			bookInDB.setBook_description(book.getBook_description());
			bookInDB.setReleasedate(book.getReleasedate());
			bookInDB.setPage(book.getPage());
		} else {
			throw new RuntimeException("Book not found with ID: " + id);
		}

		bookRepository.save(bookInDB);
	}

	@Override
	public boolean isNamebookUnique(String name) {
		Book bookByNamebook = bookRepository.findByNamebook(name);
		return bookByNamebook == null;
	}

}
