package com.infopeersoft.electronicstore.repositories;

import com.infopeersoft.electronicstore.entities.Category;
import com.infopeersoft.electronicstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findBytitleContaining(String keywords);

}
