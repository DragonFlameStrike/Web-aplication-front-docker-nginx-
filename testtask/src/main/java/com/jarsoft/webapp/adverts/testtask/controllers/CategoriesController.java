package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.entity.CategoryEntity;
import com.jarsoft.webapp.adverts.testtask.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("categories")
public class CategoriesController {
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * <h1> Application page with categories</h1>
     * @param model for insert data in html
     * @return categoryEmptyView.html
     */
    @GetMapping()
    public String categoriesView(Model model){
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "layout/categoryEmptyView";
    }
    /**
     * <h1> Application page with categories and edit list for current</h1>
     * @param cid for define current category
     * @param model for insert data in html
     * @return category-edit.html
     */
    @GetMapping("/{cid}")
    public String show(@PathVariable("cid") Long cid, Model model) {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        CategoryEntity currCategory = categoryRepository.findById(cid).orElseThrow();
        model.addAttribute("currCategory",currCategory);
        return "layout/category-edit";
    }
    /**
     * <h1> Post Request to change data in current category</h1>
     * @param cid for define current category
     * @param action field from buttons Save/Delete
     * @param name put in CategoryEntity.name
     * @param idRequest put in CategoryEntity.idRequest
     * @return categoryEmptyView.html
     */
    @PostMapping("/{cid}")
    public String updateCategory(
            @PathVariable(value = "cid") long cid,
            @RequestParam String action,
            @RequestParam String name,
            @RequestParam String idRequest){
        CategoryEntity category = categoryRepository.findById(cid).orElseThrow();
        if(action.equals("Save")){
            category.setName(name);
            category.setIdRequest(idRequest);
            categoryRepository.save(category);
        }
        else{
            categoryRepository.delete(category);
        }
        return "redirect:/categories";
    }

    /**
     * <h1>  Application page with categories and create list</h1>
     * @param model for insert data in html
     * @return category-create.html
     */
    @GetMapping("/create")
    public String create(Model model) {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "layout/category-create";
    }
    /**
     * <h1> Post Request to create data in table CategoryEntity</h1>
     * @param name put in CategoryEntity.name
     * @param idRequest put in CategoryEntity.idRequest
     * @return bannerEmptyView.html
     */
    @PostMapping("/create")
    public String createCategory(
            @RequestParam String name,
            @RequestParam String idRequest){
        CategoryEntity category = new CategoryEntity(name,idRequest);
        categoryRepository.save(category);
        return "redirect:/categories";
    }
}
