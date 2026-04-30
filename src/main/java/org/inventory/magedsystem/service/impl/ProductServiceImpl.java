package org.inventory.magedsystem.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.ProductDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.entity.Category;
import org.inventory.magedsystem.entity.Product;
import org.inventory.magedsystem.exceptions.NotFoundException;
import org.inventory.magedsystem.repository.CategoryRepository;
import org.inventory.magedsystem.repository.ProductRepository;
import org.inventory.magedsystem.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    private static final String IMAGE_UPLOAD_DIR = System.getProperty("user.dir") + "/product-image/";

    @Override
    public Response saveProduct(ProductDto productDto, MultipartFile imageFile) {
        Category category=categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(()->new NotFoundException("Category not Found"));

        //map productdto to product entity

        Product productToSave= Product.builder()
                .category(category)
                .name(productDto.getName())
                .sku(productDto.getSku())

                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .StockQuentity(productDto.getStockQunantity())
                .build();

        if(imageFile!=null){
            String imagePath=saveImage(imageFile);
            productToSave.setImageUrl(imagePath);
        }
        //save product in DB
        productRepository.save(productToSave);
        return  Response.builder()
                .status(200)
                .message("product created successfully")
                .build();

    }

    @Override
    public Response updateProduct(ProductDto productDto, MultipartFile imageFile) {
       Product exitingProduct=productRepository.findById(productDto.getProductId())
               .orElseThrow(()->new NotFoundException("product not found"));
       //check  if image is axxociate dwith updated request
        if(imageFile!=null&&!imageFile.isEmpty()){
            String imagePath=saveImage( imageFile);
            exitingProduct.setImageUrl(imagePath);

        }

        //check for category update
        if(productDto.getCategoryId()!=null&&productDto.getCategoryId()>0){
           Category category= categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(()->new NotFoundException("category not found"));
           exitingProduct.setCategory(category);
        }
        //checks and upate fileds
        if(productDto.getName()!=null&&!productDto.getName().isBlank()){
            exitingProduct.setName(productDto.getName());
        }
        if(productDto.getSku()!=null&&!productDto.getSku().isBlank()){
            exitingProduct.setSku(productDto.getSku());
        }
        if(productDto.getDescription()!=null&&!productDto.getDescription().isBlank()){
            exitingProduct.setDescription(productDto.getDescription());
        }
        if(productDto.getPrice()!=null&& productDto.getPrice().toString().compareTo(String.valueOf(BigDecimal.ZERO))>=0){
            exitingProduct.setPrice(productDto.getPrice());
        }

        if(productDto.getStockQunantity()!=null&& productDto.getStockQunantity().intValue()>=0){
            exitingProduct.setStockQuentity(productDto.getStockQunantity());
        }
     //save the updated fileds
        productRepository.save(exitingProduct);
        return Response.builder()
                .status(200)
                .message("product updated successfully")
                .build();
    }

    @Override
    public Response getAllproducts() {
        List<Product> products=productRepository.findAll(Sort.by(Sort.Direction.DESC,"Id"));
        List<ProductDto> productDtos = products.stream()
                .map(product -> {
                    ProductDto dto = modelMapper.map(product, ProductDto.class);
                    dto.setProductId(product.getId());
                    dto.setId(product.getId());
                    if (product.getCategory() != null) {
                        dto.setCategoryId(product.getCategory().getId());
                    }
                    return dto;
                })
                .toList();


        return Response.builder()
                .status(200)
                .message("success")
                .productDtos(productDtos)
                .build();
    }

    @Override
    public Response getproductById(Long id) {
        Product product=productRepository.findById(id).orElseThrow(()->new NotFoundException("product not found"));
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        productDto.setProductId(product.getId());
        productDto.setId(product.getId());
        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }
        return Response.builder()
                .status(200)
                .message("success")
                .productDto(productDto)
                .build();

    }

    @Override
    public Response deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(()->new NotFoundException("product not found to delete it"));
        productRepository.deleteById(id);


        return Response.builder()
                .status(200)
                .message("product successfly deleted")
                .build();
    }


    private String saveImage(MultipartFile imageFile) {
        //validate image
        if (!imageFile.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("file must be an image");

        }
        //create the directory to store image if it doesnot exist

        File directory = new File(IMAGE_UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
            log.info("directory Created Successfully");
        }
        //generate unique file name for image
        String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        String imagePath = IMAGE_UPLOAD_DIR + uniqueFileName;
        try {
            File destinationfile = new File(imagePath);
            imageFile.transferTo(destinationfile);//we are transfering ->writing to this file->destinationfile


        } catch (Exception e) {
            throw new IllegalArgumentException("failed to save image file", e);

        }
        return imagePath;

    }
}
