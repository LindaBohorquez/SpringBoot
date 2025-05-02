package com.api.demo.service;

import com.api.demo.dto.ProductDto;
import com.api.demo.model.Product;
import com.api.demo.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CachePut(value = "PRODUCT_CACHE", key = "#result.id()")
    public ProductDto createProduct(ProductDto productDto) {
        var product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());
    }

    @Cacheable(value = "PRODUCT_CACHE", key = "#productId")
    public ProductDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Cannot find product with id " + productId));
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
            .map(p -> new ProductDto(p.getId(), p.getName(), p.getPrice()))
            .collect(Collectors.toList());
    }

    @CachePut(value = "PRODUCT_CACHE", key = "#result.id()")
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.id())
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + productDto.id()));

        product.setName(productDto.name());
        product.setPrice(productDto.price());

        Product updatedProduct = productRepository.save(product);
        return new ProductDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice());
    }

    @CacheEvict(value = "PRODUCT_CACHE", key = "#productId")
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
