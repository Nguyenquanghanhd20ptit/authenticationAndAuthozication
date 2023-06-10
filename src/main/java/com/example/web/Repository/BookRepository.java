package com.example.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.web.data.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findByNamebook(String namebook);
}
