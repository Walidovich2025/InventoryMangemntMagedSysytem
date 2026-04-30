package org.inventory.magedsystem.service;

import org.inventory.magedsystem.dto.ProductDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.SupplierDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response saveProduct(ProductDto productDto, MultipartFile imageFile);
    Response updateProduct(ProductDto productDto, MultipartFile imageFile);

    Response getAllproducts();
    Response getproductById(Long id);
    Response deleteProduct(Long id);

}
