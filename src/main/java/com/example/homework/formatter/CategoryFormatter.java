package com.example.homework.formatter;

import com.example.homework.model.Category;
import com.example.homework.service.Iplm.CategoryService;
import org.springframework.format.Formatter;

import java.util.Locale;

public class CategoryFormatter implements Formatter<Category> {

    private CategoryService categoryService;

    public CategoryFormatter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Category parse(String text, Locale locale) {
        return categoryService.findById(Long.parseLong(text));
    }

    @Override
    public String print(Category object, Locale locale) {
        return null;
    }
}
