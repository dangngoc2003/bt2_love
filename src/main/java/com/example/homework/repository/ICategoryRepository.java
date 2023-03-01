package com.example.homework.repository;

import com.example.homework.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
