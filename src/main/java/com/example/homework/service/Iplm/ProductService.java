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
import java.util.ArrayList;
import java.util.List;
@Service
public class ProductService {
    @Value("${upload}")
    private String uploadPath;
    @Autowired
    IProductRepository iProductRepository;
    List<Product> listCart=new ArrayList<>();
    public List<Product> findAllProduct (){
        return iProductRepository.findAll();
    }
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
    public Page<Product> findAll(String name, Long category,Pageable pageable) {
            return iProductRepository.findNameAndC("%" + name + "%",category,pageable);
    }
    public Page<Product> findName(String name,Pageable pageable) {
        return iProductRepository.findName("%" + name + "%",pageable);
    }
    public List<Product> findListCart(){
        return listCart;
    }
    public Product findIdCart(Long id){
        for (Product p:listCart) {
            if (p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
    public Product choiceCart(Long id){
    Product product=findById(id);
    product.setQuantity(product.getQuantity()-1);
    iProductRepository.save(product);
    if (findIdCart(id)!=null){
        findIdCart(id).setQuantity(findIdCart(id).getQuantity()+1);
    }else {
        listCart.add(new Product(product.getId(),product.getName(),product.getPrice(),1,product.getDescription(),product.getImagePath(),product.getCategory()));
    }
    return null;
    }
    public void deleteCard(Long id){
        Product product=findIdCart(id);
        listCart.remove(product);
    }

}
