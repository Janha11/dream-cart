package com.synergy.dreamShop.service.product;

import com.synergy.dreamShop.model.Product;
import com.synergy.dreamShop.request.AddProductRequest;
import com.synergy.dreamShop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);

    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product,Long productId);

    List<Product> getAllProducts();
    List<Product>getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String Category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category,String name);
    Long countProductsByBrandAndName(String brand,String name);


}
