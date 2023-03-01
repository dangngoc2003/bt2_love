package com.example.homework.service.Iplm;

import com.example.homework.model.Product;
import com.example.homework.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
@Service
public class ProductService {
    @Value("${upload}")
    private String uploadPath;
    @Autowired
    IProductRepository iProductRepository;
    public Page<Product> findAll(Pageable pageable){
        return iProductRepository.findAll(pageable);
    }
    public Product findById(Long id){
        return iProductRepository.findById(id).orElse(null);
    }
    public void save(Product product){
        product.setImagePath(getFileName(product));
    iProductRepository.save(product);
    }
    public void delete(Long id){
        Product product=findById(id);
        iProductRepository.delete(product);
    }
    private String getFileName(Product product) {
        MultipartFile image = product.getImage();
        String fileName = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(uploadPath + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileName;
    }
}
