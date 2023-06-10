package com.example.web.data.Entity;

import javax.persistence.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookcode")
	private int bookcode;

	@Column(nullable = false, unique = true)
	@NotEmpty(message = "Empty Name book")
	private String namebook;

	@Column(nullable = false)
	@NotEmpty(message = "Empty Author")
	private String author;

	@Column(nullable = false)
	@NotEmpty(message = "Empty Title")
	private String title;;

	@Column(nullable = false)
	@NotEmpty(message = "Cannot be empty")
	@Size(min = 10, message = "Wrong date format")
	private String releasedate;

	@Column(nullable = true, length = 1000)
	@NotEmpty(message = "Empty Describe")
	private String book_description;

	@Column(nullable = false)
	@Pattern(regexp = "[0-9]+", message = "Only numbers allow")
	private String page;

	@Column(nullable = true, length = 255)
	private String photo;

	public Book() {
	}

	public Book(int bookcode, String namebook, String author, String tile, String book_description, String releasedate,
			String page) {
		super();
		this.bookcode = bookcode;
		this.namebook = namebook;
		this.author = author;
		this.title = title;
		this.book_description = book_description;
		this.releasedate = releasedate;
		this.page = page;
	}

	public int getBookcode() {
		return bookcode;
	}

	public void setBookcode(int idbook) {
		this.bookcode = idbook;
	}

	public String getNamebook() {
		return namebook;
	}

	public void setNamebook(String name) {
		this.namebook = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBook_description() {
		return book_description;
	}

	public void setBook_description(String book_description) {
		this.book_description = book_description;
	}

	public String getReleasedate() {
		return releasedate;
	}

	public void setReleasedate(String release) {
		this.releasedate = release;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
