package com.example.irena.repository;

import com.example.irena.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity , Integer> {

    List<BookEntity> findAllByActive(boolean active);
    BookEntity findByActive(boolean active);
}
