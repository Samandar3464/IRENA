package com.example.irena.repository;

import com.example.irena.entity.NewsEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    List<NewsEntity> findAllByActive(boolean active, PageRequest pageRequest);
    Optional<NewsEntity> findByIdAndActive(Long id, boolean active);
}
