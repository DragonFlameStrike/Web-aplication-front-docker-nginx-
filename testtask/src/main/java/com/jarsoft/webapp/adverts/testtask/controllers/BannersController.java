package com.jarsoft.webapp.adverts.testtask.controllers;

import com.jarsoft.webapp.adverts.testtask.dao.BannersDAO;
import com.jarsoft.webapp.adverts.testtask.entity.BannerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{bid}")
public class BannersController {

    private final BannersDAO bannersDAO;

    @Autowired
    public BannersController(BannersDAO bannersDAO) {
        this.bannersDAO = bannersDAO;
    }

    @GetMapping("")
    public String show(@PathVariable("bid") int bid, Model model) {
        model.addAttribute("banner", bannersDAO.show(bid));
        return "layout/banners";
    }

//    @GetMapping("/new")
//    public String newBanner(@ModelAttribute("person") BannerEntity banner) {
//        return "people/new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()){
//            System.out.println("!!!!!!!!!!!");
//            return "people/new";
//        }
//
//        personDAO.save(person);
//        return "redirect:/people";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("person", personDAO.show(id));
//        return "people/edit";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        if (bindingResult.hasErrors())
//            return "people/edit";
//
//        personDAO.update(id, person);
//        return "redirect:/people";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        personDAO.delete(id);
//        return "redirect:/people";
//    }
}
