package org.example.sigaut_backend.services;

import org.example.sigaut_backend.config.ApiResponse;
import org.example.sigaut_backend.models.Category;
import org.example.sigaut_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
    private static final String CLAVE_EXISTS_MESSAGE = "La clave ya está registrado";
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "Categoria no encontrado";

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getAll() {
        return new ResponseEntity<>(new ApiResponse(categoryRepository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> new ResponseEntity<>(new ApiResponse(category, HttpStatus.OK), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ApiResponse(CATEGORY_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> getCategoryByClave(String clave) {
        return categoryRepository.findByClave(clave)
                .map(category -> new ResponseEntity<>(new ApiResponse(category, HttpStatus.OK), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new ApiResponse(CATEGORY_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST));
    }

    @Transactional
    public ResponseEntity<ApiResponse> createCategory(Category category) {
        if (categoryRepository.findByClave(category.getClave()).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(CLAVE_EXISTS_MESSAGE, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        Category savedCategory = categoryRepository.save(category);
        return new ResponseEntity<>(new ApiResponse(savedCategory, HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ApiResponse> updateCategory(Category updateCategory) {
        return categoryRepository.findById(updateCategory.getId())
                .map(existingCategory -> {
                    if (updateCategory.getName() != null) existingCategory.setName(updateCategory.getName());
                    if (updateCategory.getDescription() != null) existingCategory.setDescription(updateCategory.getDescription());
                    if (!updateCategoryClaveIfValid(updateCategory, existingCategory)) {
                        return new ResponseEntity<>(
                                new ApiResponse(CLAVE_EXISTS_MESSAGE, HttpStatus.BAD_REQUEST),
                                HttpStatus.BAD_REQUEST);
                    }

                    Category savedCategory = categoryRepository.save(existingCategory);
                    return new ResponseEntity<>(new ApiResponse(savedCategory, HttpStatus.OK), HttpStatus.OK);

                })
                .orElseGet(() -> new ResponseEntity<>(
                        new ApiResponse(CATEGORY_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST));
    }

    @Transactional
    public ResponseEntity<ApiResponse> deleteCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    // Verificar si tiene productos asociados
                    if (category.getProducts() != null && !category.getProducts().isEmpty()) {
                        return new ResponseEntity<>(
                                new ApiResponse("No se puede eliminar la categoría porque tiene productos asociados", HttpStatus.BAD_REQUEST),
                                HttpStatus.BAD_REQUEST
                        );
                    }

                    categoryRepository.delete(category);
                    return new ResponseEntity<>(new ApiResponse("Categoría eliminada correctamente", HttpStatus.OK), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(
                        new ApiResponse(CATEGORY_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
                        HttpStatus.BAD_REQUEST
                ));
    }


    private boolean updateCategoryClaveIfValid(Category updatedCategory, Category existingCategory) {
        if (updatedCategory.getClave() != null && !updatedCategory.getClave().isEmpty()
                && !updatedCategory.getClave().equals(existingCategory.getClave())) {
            if (categoryRepository.findByClave(updatedCategory.getClave()).isPresent()) {
                return false;
            }
            existingCategory.setClave(updatedCategory.getClave());
        }
        return true;
    }
}
