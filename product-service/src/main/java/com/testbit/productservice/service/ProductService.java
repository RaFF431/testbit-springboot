package com.testbit.productservice.service;

import com.testbit.productservice.entity.Product;
import com.testbit.productservice.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    // get products
    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return repository.findAll();
    }

    // get product id
    @Transactional(readOnly = true)
    public Product findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produk Tidak Ada")); // jika produk null return message
    }

    //create
    @Transactional
    public Product create(Product product){

        if(product.getName() == null || product.getName().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "isi nama barang");
        }

        if(product.getPrice().doubleValue() < 0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "value tidak boleh dibawah 0");
        }

        return repository.save(product);
    }
    public Product reduceStock(Long id, Integer qty){

        Product product = findById(id);

        if(product.getStock() < qty){
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Not enough stock");
        }

        product.setStock(product.getStock() - qty);

        return repository.save(product);
    }

    //update
    @Transactional
    public Product update(Long id, Product updatedProduct) {

        Product product = findById(id);

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());

        return repository.save(product);
    }

    //remove
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Product updateStock(Long id, Integer qty) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getStock() < qty){
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - qty);

        return repository.save(product);
    }
}
