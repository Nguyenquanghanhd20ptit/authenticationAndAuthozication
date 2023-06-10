package com.example.web.Service.book;

import java.util.List;
import com.example.web.data.Entity.Book;

public interface BookService {
	List<Book> getAllBook();

	void saveBook(Book book);

	Book getBookById(int id);

	void deleteBookById(int id);

	void updateBook(int id, Book book);

	boolean isNamebookUnique(String namebook);
}
