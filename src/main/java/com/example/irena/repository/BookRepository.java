package com.example.irena.repository;

import com.example.irena.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity , Integer> {
}
