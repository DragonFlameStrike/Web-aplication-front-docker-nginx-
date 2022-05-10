package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public List<CategoryEntity> categoriesView(){
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        return Streamable.of(categories).toList();
    }

    @GetMapping("/{cid}")
    public CategoryEntity show(@PathVariable("cid") Long cid) {
        CategoryEntity currCategory = categoryRepository.findById(cid).orElseThrow();
        return currCategory;
    }

    @PostMapping("/{cid}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long cid, @RequestBody CategoryEntity categoryDetails){
        CategoryEntity category = categoryRepository.findById(cid).orElseThrow();
        category.setName(categoryDetails.getName());
        category.setIdRequest(categoryDetails.getIdRequest());
        CategoryEntity updatedCategoryEntity = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategoryEntity);
    }


    @GetMapping("/create")
    public String create() {return "";}

    @PostMapping("/create")
    public CategoryEntity createCategory(@RequestBody CategoryEntity category) {
        return categoryRepository.save(category);
    }
    @DeleteMapping("/{cid}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long cid){
        CategoryEntity category = categoryRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + cid));
        categoryRepository.delete(category);
        return ResponseEntity.ok(true);
    }
}
