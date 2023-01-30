package com.example.taskApi.taskApi.Repository;

import com.example.taskApi.taskApi.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findAllByTypeContainingIgnoreCase(String type);

    public List<Category> findAllByKeywordContainingIgnoreCase(String keyword);

}
