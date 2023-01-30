package com.example.taskApi.taskApi.Controller;

import com.example.taskApi.taskApi.Model.Category;
import com.example.taskApi.taskApi.Repository.CategoryRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable long id) {
        return categoryRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Category>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(categoryRepository.findAllByTypeContainingIgnoreCase(type));
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<Category>> getByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(categoryRepository.findAllByKeywordContainingIgnoreCase(keyword));
    }

    @PostMapping
    public ResponseEntity<Category> registerCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
    }

    @PutMapping
    public ResponseEntity<Category> updateCategory(@Validated @RequestBody Category category) {
        return categoryRepository.findById(category.getId())
                .map(resp -> { return ResponseEntity.ok().body(categoryRepository.save(category));})
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        return categoryRepository.findById(id)
                .map(resp -> { categoryRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();})
                .orElse(ResponseEntity.notFound().build());
    }

}
