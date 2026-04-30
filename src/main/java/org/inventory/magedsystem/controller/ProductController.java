package org.inventory.magedsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.ProductDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.service.CategoryService;
import org.inventory.magedsystem.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAllProducts() {
        return ResponseEntity.ok(productService.getAllproducts());

    }

    @GetMapping("/all/{id}")

    public ResponseEntity<Response> getproductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getproductById(id));

    }




    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @PathVariable Long id,
            @RequestParam (value = "imagefile",required = false) MultipartFile imagefile,
            @RequestParam (value = "name",required = false) String name,

            @RequestParam (value = "sku",required = false) String sku,

            @RequestParam (value = "price",required = false)BigDecimal price,

            @RequestParam (value = "StockQuentity",required = false)BigInteger StockQuentity,
            @RequestParam (value = "categoryId",required = true) Long categoryId,

            @RequestParam(value = "description",required = false) String description) {

        ProductDto productDto= new ProductDto();
        productDto.setId(id);
        productDto.setProductId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setStockQunantity(StockQuentity);
        productDto.setSku(sku);
        productDto.setCategoryId(categoryId);
        return ResponseEntity.ok(productService.updateProduct(productDto,imagefile));

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> saveProduct(
            @RequestParam ("imagefile") MultipartFile imagefile,
            @RequestParam ("name") String name,

            @RequestParam ("sku") String sku,

           @RequestParam ("price")BigDecimal price,

          @RequestParam ("StockQuentity")BigInteger StockQuentity,
          @RequestParam ("categoryId") Long categoryId,
          @RequestParam(name = "description",required = false) String description) {

        ProductDto productDto= new ProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setStockQunantity(StockQuentity);
        productDto.setSku(sku);
        productDto.setCategoryId(categoryId);
        return ResponseEntity.ok(productService.saveProduct(productDto,imagefile));

    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));

    }





}
