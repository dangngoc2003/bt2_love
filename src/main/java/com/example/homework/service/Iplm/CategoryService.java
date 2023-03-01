package com.example.homework.service.Iplm;

import com.example.homework.model.Category;
import com.example.homework.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    ICategoryRepository iCategoryRepository;
    public List<Category> findAll(){
        return iCategoryRepository.findAll();
    }
    public Category findById(Long id){
        return iCategoryRepository.findById(id).orElse(null);
    }
}
