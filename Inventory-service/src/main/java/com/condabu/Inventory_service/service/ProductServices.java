package com.condabu.Inventory_service.service;


import com.condabu.Inventory_service.common.ServiceResponse;
import com.condabu.Inventory_service.dto.ImageRepository;
import com.condabu.Inventory_service.dto.ProductDto;
import com.condabu.Inventory_service.dto.ProductImageDTO;
import com.condabu.Inventory_service.dto.ProductRepository;
import com.condabu.Inventory_service.entity.Product;
import com.condabu.Inventory_service.entity.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public ResponseEntity<Object> uploadImages(Long productId, List<ProductImageDTO> imageDTOS) {
        try{
            Product product = productRepository.findProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

            // Create ProductImage objects
            List<ProductImage> images = imageDTOS.stream()
                    .map(dto -> {
                        ProductImage image = new ProductImage();
                        image.setProduct(product);  // Set the Product instance
                        image.setImageUrl(dto.getImageUrl());
                        image.setImageType(dto.getImageType());
                        return image;
                    })
                    .collect(Collectors.toList());
          imageRepository.saveAll(images);
          return ResponseEntity.status(HttpStatus.CREATED).body(new ServiceResponse("success", "product images updated successfully "));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ServiceResponse("error", "An Internal server error occurred"));
        }
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

            try{
                productRepository.save(existingProduct);
            }catch (Exception e){
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse("error", "Error occurred while processing request contact system administrator"+" "+ e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ServiceResponse("success", "Product updated successfully"));
        }else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServiceResponse("failed", "Product not found"));
        }

    }


    public List<ProductDto> listAllProducts(){
        List<Product> products = productRepository.findAllWithImages();
//
//        if (products.isEmpty()){
//            return "";// ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServiceResponse("failed", "No products found"));
//        }
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public ResponseEntity<Object> deleteProduct(Long id){
        Optional<Product> existingProduct = productRepository.findProductById(id);

        if(existingProduct.isPresent()){
            try{
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ServiceResponse("success", "product deleted successfully"));
        }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse("error", "Error occurred while processing request contact system administrator" +e.getMessage()));
            }

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServiceResponse("failed", "Product with id"+" "+ id+" "+"is not found"));
        }
    }

    public ProductDto getProductById(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new IllegalArgumentException("Product not found with id" + productId));

        ProductDto productDto = new ProductDto();

        productDto.setId(product.getId());
        productDto.setSku(product.getSku());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setBrand(product.getBrand());
        productDto.setSupplierId(product.getSupplierId());
        productDto.setCreated(product.getCreated());
        productDto.setUpdated(product.getUpdated());
        productDto.setImages(product.getImages().stream()
                .map(img ->{
                    ProductImageDTO imageDTO = new ProductImageDTO();
                    imageDTO.setImageUrl(img.getImageUrl());
                    imageDTO.setImageType(img.getImageType());
                    return imageDTO;
                }).collect(Collectors.toList()));
        return productDto;
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setSku(product.getSku());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setBrand(product.getBrand());
        productDto.setSupplierId(product.getSupplierId());
        productDto.setCreated(product.getCreated());
        productDto.setUpdated(product.getUpdated());
        productDto.setImages(product.getImages().stream()
                .map(img -> {
                    ProductImageDTO imageDto = new ProductImageDTO();
                    imageDto.setImageUrl(img.getImageUrl());
                    imageDto.setImageType(img.getImageType());
                    return imageDto;
                })
                .collect(Collectors.toList()));
        return productDto;
    }
}
