package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.exception.BadNameException;
import com.jarsoft.webapp.adverts.testtask.exception.NotUniqueNameException;
import com.jarsoft.webapp.adverts.testtask.exception.ResourceNotFoundException;
import com.jarsoft.webapp.adverts.testtask.exception.WrongDeleteException;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import com.jarsoft.webapp.adverts.testtask.security.SqlInjectionSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <h1>This RestController exists to work with categories and return some information to CategoryService</h1>
 *
 */
@RestController()
@RequestMapping("/root/api/categories")
public class CategoriesController {

    private CategoryRepository categoryRepository;
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * <h1>This function called by CategoryService to get all not deleted Categories</h1>
     *
     * @see <a href="..src/main/js/services/CategoryService.js">src/main/js/services/CategoryService.js</a>
     * @return {@code List<CategoryEntity>} not deleted categories from category_entity table
     *
     */
    @GetMapping("/search/")
    public List<CategoryEntity> viewAllCategories(){
        Iterable<CategoryEntity> categories = categoryRepository.findAllNotDeleted();
        return StreamSupport.stream(categories.spliterator(), false)
                .collect(Collectors.toList());
    }
    /**
     * <h1>This function called by CategoryService to get Categories by name</h1>
     *
     * @param searchValue PathVariable
     * @return List<CategoryEntity> filtered by searchValue
     * @throws BadNameException if searchValue contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/CategoryService.js">src/main/js/services/CategoryService.js</a>
     */
    @GetMapping("/search/{searchValue}")
    public List<CategoryEntity> viewCertainCategories(@PathVariable String searchValue) throws BadNameException {

        if(SqlInjectionSecurity.check(searchValue)) throw new BadNameException();
        Iterable<CategoryEntity> categories = categoryRepository.findAllNotDeletedBySearch(searchValue.toLowerCase());
        return StreamSupport.stream(categories.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * <h1>This function called by CategoryService to get Category by id</h1>
     *
     * @param cid CategoryEntity.IdCategory
     * @return CategoryEntity or ResourceNotFoundException
     * @throws BadNameException if cid contains bad symbols
     * @throws ResourceNotFoundException if Category with such bid not exist
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/CategoryService.js">src/main/js/services/CategoryService.js</a>
     */
    @GetMapping("/{cid}")
    public CategoryEntity show(@PathVariable("cid") Long cid) throws BadNameException {
        if(SqlInjectionSecurity.check(String.valueOf(cid))) throw new BadNameException();
        return categoryRepository.findById(cid).orElseThrow(() -> new ResourceNotFoundException("Category not exist with id :" + cid));
    }

    /**
     * <h1>This function called by CategoryService to update Category by id</h1>
     *
     * @param cid CategoryEntity.IdCategory
     * @param categoryDetails new CategoryEntity
     * @return ResponseEntity
     * @throws NotUniqueNameException if category with same name already exist
     * @throws BadNameException if  CategoryEntity.name or CategoryEntity.IdRequest contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/CategoryService.js">src/main/js/services/CategoryService.js</a>
     */
    @PostMapping("/{cid}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable Long cid, @Valid @RequestBody CategoryEntity categoryDetails) throws NotUniqueNameException, BadNameException {
        if(SqlInjectionSecurity.check(categoryDetails.getName())) throw new BadNameException();
        if(SqlInjectionSecurity.check(String.valueOf(cid))) throw new BadNameException();
        if(SqlInjectionSecurity.check(categoryDetails.getIdRequest())) throw new BadNameException();

        Iterable<CategoryEntity> categoriesByName = categoryRepository.findAllNotDeletedByName(categoryDetails.getName());
        for (CategoryEntity category: categoriesByName) {
            if(!Objects.equals(category.getIdCategory(), cid)) {
                if (category.getName().equals(categoryDetails.getName())) {  //You should re-control result, because query used LOWERCASE context
                    throw new NotUniqueNameException();
                }
            }
        }

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

    /**
     * <h1>This function called by CategoryService to create Category</h1>
     *
     * @param newCategory CategoryEntity with completed fields
     * @return CategoryEntity which was created
     * @throws NotUniqueNameException if category with same name already exist
     * @throws BadNameException if  CategoryEntity.name or CategoryEntity.IdRequest contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/CategoryService.js">src/main/js/services/CategoryService.js</a>
     */
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

    /**
     * <h1>This function called by CategoryService to delete Category without banners by id, </h1>
     *
     * @param cid Category id which will be deleted
     * @return ResponseEntity
     * @throws ResourceNotFoundException if Category with such cid not exist
     * @throws WrongDeleteException if Category have a banners
     * @throws BadNameException if cid contains bad symbols
     * @see SqlInjectionSecurity
     * @see <a href="..src/main/js/services/CategoryService.js">src/main/js/services/CategoryService.js</a>
     */
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
