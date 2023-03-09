package com.example.homework.controller;

import com.example.homework.model.Category;
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

import java.util.Optional;

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
       modelAndView.addObject("categories",categoryService.findAll());
        modelAndView.addObject("listCard",productService.findListCart());

       return modelAndView;
    }
    @GetMapping("/buy/{id}")
    public String choiceCart(@PageableDefault(value = 3) Pageable pageable,@PathVariable Long id,Model model){
       productService.choiceCart(id);
        return "redirect:/products";
    }

    @PostMapping("/createC")
    public String  createC(@ModelAttribute Category category){
        categoryService.save(category);
        return "redirect:/products/category";
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
    @GetMapping("/deleteCard/{id}")
    public String deleteCard(@PathVariable Long id){
        productService.deleteCard(id);
        return "redirect:/products";

    }
    @GetMapping("/search")
    public ModelAndView search(@PageableDefault(value = 3)@RequestParam("search") Optional<String> name, @RequestParam("category")String category_id ,Pageable pageable){
    ModelAndView modelAndView=new ModelAndView("/product/list");
    Long category=Long.valueOf(category_id);
        if (category!=0){
            modelAndView.addObject("search",name.get() );
            modelAndView.addObject("categories",categoryService.findAll());
            modelAndView.addObject("products",productService.findAll(name.get(),category,pageable));
        }else {
            modelAndView.addObject("categories",categoryService.findAll());
            modelAndView.addObject("products",productService.findName(name.get(),pageable));
            modelAndView.addObject("category",categoryService.findById(category));
        }
        return modelAndView;

    }



}
