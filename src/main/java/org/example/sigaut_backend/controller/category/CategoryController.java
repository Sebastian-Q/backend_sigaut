package org.example.sigaut_backend.controller.category;

import org.example.sigaut_backend.config.ApiResponse;
import org.example.sigaut_backend.models.Category;
import org.example.sigaut_backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"*"})
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/clave/{clave}")
    public ResponseEntity<ApiResponse> getCategoryByClave(@PathVariable String clave) {
        return categoryService.getCategoryByClave(clave);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category updatedCategory) {
        return categoryService.updateCategory(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        return categoryService.deleteCategoryById(id);
    }
}
