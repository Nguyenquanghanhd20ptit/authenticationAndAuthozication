package com.example.web.Controller;

import com.example.web.upload.service.ICloudinaryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;

import com.example.web.data.Entity.Book;
import com.example.web.Repository.UserRepository;
import com.example.web.Service.book.BookService;

@Controller
public class AppController {

	private int tmpId = 0;
	private final BookService bookService;
	private final UserRepository userRepo;
	private final ICloudinaryService cloudinaryService;

	public AppController(BookService bookService,
						 UserRepository userRepo,
						 ICloudinaryService cloudinaryService) {
		this.bookService = bookService;
		this.userRepo = userRepo;
		this.cloudinaryService = cloudinaryService;
	}

	// Books Controller --

	@GetMapping("/api/admin/books")
	public String showListBook(Model model) {
		model.addAttribute("books", bookService.getAllBook());
		return "books";
	}

	// Create Book
	@GetMapping("/api/admin/book/newBook")
	public String formAddBook(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "bookForm";
	}

	@PostMapping("/api/admin/save")
	public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult result,
			@RequestParam("image") MultipartFile image, ModelMap model) throws IOException {

		if (result.hasErrors() && image.isEmpty()) {
			return "bookForm";
		}
		try {
			String url = cloudinaryService.uploadCloudinary(image);
			book.setPhoto(url);
			model.addAttribute("INFOR", book);
			bookService.saveBook(book);
			return "redirect:/api/admin/books";
		}catch (IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "bookForm";
		}
	}

	// Update book
	@GetMapping("/api/admin/book/editBook/{id}")
	public String formViewBook(@PathVariable(value = "id") int id, Model model) {
		tmpId = id;
		Book book = bookService.getBookById(tmpId);
		if (book == null) {
			return "error";
		}
		model.addAttribute("book", book);
		return "editBook";
	}

	@PutMapping("/api/admin/edit/{id}")
	public String updateBook(@PathVariable(value = "id") int id, @ModelAttribute("book") @Valid Book book,
			BindingResult result) {
		if (result.hasErrors()) {
			book.setBookcode(tmpId);
			return "editBook";
		}

		bookService.updateBook(id, book);
		return "redirect:/api/admin/books";
	}

	// Delete book
	@GetMapping("/api/admin/book/delete/{id}")
	public String formDeleteBook(@PathVariable(value = "id") int id, Model model) {
		Book book = bookService.getBookById(id);
		if (book == null) {
			return "error";
		}
		model.addAttribute("book", book);
		return "deleteBook";
	}

	@DeleteMapping("/api/admin/delete/{id}")
	public String deleteBook(@PathVariable(name = "id") int id) {
		bookService.deleteBookById(id);
		return "redirect:/api/admin/books";
	}
}
