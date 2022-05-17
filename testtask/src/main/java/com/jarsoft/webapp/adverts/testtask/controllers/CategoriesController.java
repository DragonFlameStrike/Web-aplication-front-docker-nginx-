package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.exception.BadNameException;
import com.jarsoft.webapp.adverts.testtask.exception.NotUniqueNameException;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.exception.WrongDeleteException;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import com.jarsoft.webapp.adverts.testtask.security.SqlInjectionSecurity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RestController()
@RequestMapping("/root/api/categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/search/")
    public List<CategoryEntity> viewAllCategories(){
        Iterable<CategoryEntity> categories = categoryRepository.findAllNotDeleted();
        return StreamSupport.stream(categories.spliterator(), false)
                .collect(Collectors.toList());
    }
    @GetMapping("/search/{searchValue}")
    public List<CategoryEntity> viewCertainCategories(@PathVariable String searchValue) throws BadNameException {

        if(SqlInjectionSecurity.check(searchValue)) throw new BadNameException();
        Iterable<CategoryEntity> categories = categoryRepository.findAllNotDeletedBySearch(searchValue.toLowerCase());
        return StreamSupport.stream(categories.spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{cid}")
    public CategoryEntity show(@PathVariable("cid") Long cid) throws BadNameException {
        if(SqlInjectionSecurity.check(String.valueOf(cid))) throw new BadNameException();
        CategoryEntity currCategory = categoryRepository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + cid));
        return currCategory;
    }

    @PostMapping("/{cid}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long cid, @Valid @RequestBody CategoryEntity categoryDetails) throws NotUniqueNameException, BadNameException {
        if(SqlInjectionSecurity.check(categoryDetails.getName())) throw new BadNameException();
        if(SqlInjectionSecurity.check(String.valueOf(cid))) throw new BadNameException();
        Iterable<CategoryEntity> categoriesByName = categoryRepository.findAllNotDeletedByName(categoryDetails.getName());
        for (CategoryEntity category: categoriesByName) {
            if(!Objects.equals(category.getIdCategory(), cid)) {
                if (category.getName().equals(categoryDetails.getName())) {  //You should re-control result, because query used LOWERCASE context
                    throw new NotUniqueNameException();
                }
            }
        }
        if(SqlInjectionSecurity.check(categoryDetails.getIdRequest())) throw new BadNameException();
        Iterable<CategoryEntity> categoriesByIdRequest = categoryRepository.findAllNotDeletedByIdRequest(categoryDetails.getIdRequest());
        for (CategoryEntity category: categoriesByIdRequest) {
            if(!Objects.equals(category.getIdCategory(), cid)) {
                if (category.getIdRequest().equals(categoryDetails.getIdRequest())) {  //You should re-control result, because query used LOWERCASE context
                    throw new NotUniqueNameException();
                }
            }
        }
        CategoryEntity category = categoryRepository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + cid));
        category.setName(categoryDetails.getName());
        category.setIdRequest(categoryDetails.getIdRequest());
        category.setDeleted(false);
        CategoryEntity updatedCategoryEntity = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategoryEntity);
    }


    @GetMapping("/create")
    public String create() {return "";}

    @PostMapping("/create")
    public CategoryEntity createCategory(@Valid @RequestBody CategoryEntity newCategory) throws NotUniqueNameException, BadNameException {
        if(SqlInjectionSecurity.check(newCategory.getName())) throw new BadNameException();
        Iterable<CategoryEntity> categories = categoryRepository.findAllNotDeletedByName(newCategory.getName());
        for (CategoryEntity category: categories) {
            if (category.getName().equals(newCategory.getName())) {  //You should re-control result, because query used LOWERCASE context
                throw new NotUniqueNameException();
            }
        }
        if(SqlInjectionSecurity.check(newCategory.getIdRequest())) throw new BadNameException();
        Iterable<CategoryEntity> categoriesByIdRequest = categoryRepository.findAllNotDeletedByIdRequest(newCategory.getIdRequest());
        for (CategoryEntity category: categoriesByIdRequest) {
            if (category.getIdRequest().equals(newCategory.getIdRequest())) {  //You should re-control result, because query used LOWERCASE context
                throw new NotUniqueNameException();
            }
        }
        return categoryRepository.save(newCategory);
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long cid) throws WrongDeleteException, BadNameException {
        if(SqlInjectionSecurity.check(String.valueOf(cid))) throw new BadNameException();
        CategoryEntity currCategory = categoryRepository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + cid));
        List<BannerEntity> AssociateBanners = currCategory.getBanners();
        if(!AssociateBanners.isEmpty()){
            for (BannerEntity banner:
                 AssociateBanners) {
                if(!banner.getDeleted()){
                    throw new WrongDeleteException();
                }
            }
        }
        currCategory.setDeleted(true);
        categoryRepository.save(currCategory);
        return ResponseEntity.ok(true);
    }
}
