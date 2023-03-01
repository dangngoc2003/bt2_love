package com.example.homework.controller;

import com.example.homework.model.Product;
import com.example.homework.service.Iplm.CategoryService;
import com.example.homework.service.Iplm.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/products")
@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public ModelAndView findAll(@PageableDefault(value = 3) Pageable pageable){
       ModelAndView modelAndView=new ModelAndView("/product/list");
       modelAndView.addObject("products",productService.findAll(pageable));
       return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView=new ModelAndView( "/product/form");
        modelAndView.addObject("product",new Product());
        modelAndView.addObject("categories",categoryService.findAll());
        return modelAndView;
    }
    @PostMapping("/create")
    public String create(@ModelAttribute Product product){
    productService.save(product);
    return "redirect:/products";
    }
    @GetMapping("/update/{id}")
    public String updateForm(Model model,@PathVariable Long id){
        Product product=productService.findById(id);
        model.addAttribute("product",product);
        model.addAttribute("categories",categoryService.findAll());
        return "/product/form";
    }
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Product product){
        productService.save(product);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }
    @GetMapping("/category")
    public ModelAndView findAllCategory(){
        ModelAndView modelAndView=new ModelAndView("/category/list");
        modelAndView.addObject("categories",categoryService.findAll());
        return modelAndView;
    }
    @GetMapping("/detail/{id}")
    public ModelAndView deleteCategory(@PathVariable Long id){
        ModelAndView modelAndView=new ModelAndView("/product/detail");
        modelAndView.addObject("product",productService.findById(id));
    return modelAndView;
    }



}
