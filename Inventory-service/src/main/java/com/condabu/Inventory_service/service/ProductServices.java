package com.condabu.Inventory_service.service;


import com.condabu.Inventory_service.common.ServiceResponse;
import com.condabu.Inventory_service.dto.ImageRepository;
import com.condabu.Inventory_service.entity.Product;
import com.condabu.Inventory_service.dto.ProductRepository;
import com.condabu.Inventory_service.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductServices {

    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  ImageRepository imageRepository;

    public ResponseEntity<Object> createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        try{
            if(savedProduct.getCreated()!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(new ServiceResponse("success", "Product created successfully"));
            }else {
                return ResponseEntity.status(HttpStatus.CREATED).body(new ServiceResponse("failed", "Product creation failed "));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse("error", "An internal server error occurred while creating the product"));
        }

    }

    public List<ProductImage> uploadImages(Long productId, List<String> imageUrls , List<String> imageTypes ){
        if(imageUrls.size() != imageTypes.size()){
            throw  new IllegalArgumentException("Image urls not equal to image types");
        }

        List<ProductImage> images = IntStream.range(0, imageUrls.size())
                .mapToObj(i ->{
                    ProductImage image = new ProductImage();
                    image.setProductId(String.valueOf(productId));
                    image.setImageUrl(imageUrls.get(i));
                    image.setImageType(imageTypes.get(i));
                    return image;
                }).collect(Collectors.toList());

        return imageRepository.saveAll(images);
    }

    public ResponseEntity<Object> updateProduct(Long id, Product updatedUproduct){
        Optional<Product>  existingProductOpt = productRepository.findById(id);

        if(existingProductOpt.isPresent()){
            Product existingProduct = existingProductOpt.get();

            updatedUproduct.setCreated(existingProduct.getCreated());
            updatedUproduct.setId(existingProduct.getId());
            existingProduct.setSku(updatedUproduct.getSku());
            existingProduct.setName(updatedUproduct.getName());
            existingProduct.setDescription(updatedUproduct.getDescription());
            existingProduct.setPrice(updatedUproduct.getPrice());
            existingProduct.setCategory(updatedUproduct.getCategory());
            existingProduct.setBrand(updatedUproduct.getBrand());

            productRepository.save(existingProduct);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ServiceResponse("success", "Product updated successfully"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServiceResponse("failed", "Product not found"));
    }

}
