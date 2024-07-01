package com.skillbox.Spring_boot_redis_CRUD.repository;

import com.skillbox.Spring_boot_redis_CRUD.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
