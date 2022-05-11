package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController()
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/search/")
    public List<CategoryEntity> viewAllCategories(){
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryEntity> filteredCategories = new ArrayList<>();
        for (CategoryEntity category: categories) {
            if (category.getDeleted() == null || !category.getDeleted()) {
                filteredCategories.add(category);
            }
        }
        return filteredCategories;
    }
    @GetMapping("/search/{searchValue}")
    public List<CategoryEntity> viewCertainCategories(@PathVariable String searchValue){
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryEntity> filteredCategories = new ArrayList<>();
        for (CategoryEntity category: categories) {
            if(category.getDeleted() == null || !category.getDeleted()) {
                String name = category.getName().toLowerCase();
                searchValue = searchValue.toLowerCase();
                if (name.contains(searchValue)) {
                    filteredCategories.add(category);
                }
            }
        }
        return filteredCategories;
    }

    @GetMapping("/{cid}")
    public CategoryEntity show(@PathVariable("cid") Long cid) {
        CategoryEntity currCategory = categoryRepository.findById(cid).orElseThrow();
        return currCategory;
    }

    @PostMapping("/{cid}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long cid, @Valid @RequestBody CategoryEntity categoryDetails){
        CategoryEntity category = categoryRepository.findById(cid).orElseThrow();
        category.setName(categoryDetails.getName());
        category.setIdRequest(categoryDetails.getIdRequest());
        category.setDeleted(false);
        CategoryEntity updatedCategoryEntity = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategoryEntity);
    }


    @GetMapping("/create")
    public String create() {return "";}

    @PostMapping("/create")
    public CategoryEntity createCategory(@Valid @RequestBody CategoryEntity category) {
        return categoryRepository.save(category);
    }
    @DeleteMapping("/{cid}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long cid){
        CategoryEntity category = categoryRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + cid));
        category.setDeleted(true);
        categoryRepository.save(category);
        return ResponseEntity.ok(true);
    }
}
