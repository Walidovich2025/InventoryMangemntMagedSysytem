package org.inventory.magedsystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.TransactionDto;
import org.inventory.magedsystem.dto.TransactionRequest;
import org.inventory.magedsystem.entity.Product;
import org.inventory.magedsystem.entity.Supplier;
import org.inventory.magedsystem.entity.Transaction;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.enums.TransactionStatus;
import org.inventory.magedsystem.enums.TransactionType;
import org.inventory.magedsystem.exceptions.NameValueRequiredException;
import org.inventory.magedsystem.exceptions.NotFoundException;
import org.inventory.magedsystem.repository.ProductRepository;
import org.inventory.magedsystem.repository.SupplierRepository;
import org.inventory.magedsystem.repository.TransactionRepository;
import org.inventory.magedsystem.service.TransactionService;
import org.inventory.magedsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserService userService;




    @Override
    public Response restockInventory(TransactionRequest transactionRequest) {
       Long productId=  transactionRequest.getProductId();
       Long supplierId=transactionRequest.getSupplierId();
       Integer quantity=transactionRequest.getQuantity();
       if(supplierId==null)throw new NameValueRequiredException("supplierId is required");
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new NameValueRequiredException("product not found"));
        Supplier supplier=supplierRepository.findById(supplierId)
                .orElseThrow(()->new NameValueRequiredException("supplier not found"));
        User user=userService.getCurrentloggedinUser();

        //update stock quentity and re_save
        product.setStockQuentity(product.getStockQuentity().add(java.math.BigInteger.valueOf(quantity)));
        productRepository.save(product);



        //create transaction and save it
        Transaction transaction=Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .transactionStatus(TransactionStatus.COMPLETED)
                .product(product)
                .supplier(supplier)
                .user(user)
                .totalProducts(quantity)
                .totalprices(product.getPrice().multiply(new java.math.BigDecimal(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction restocked successfully")
                .build();

    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {
        Long productId=  transactionRequest.getProductId();

        Integer quantity=transactionRequest.getQuantity();
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new NameValueRequiredException("product not found"));

        User user=userService.getCurrentloggedinUser();

        //update stock quentity and re_save
        product.setStockQuentity(product.getStockQuentity().subtract(java.math.BigInteger.valueOf(quantity)));
        productRepository.save(product);



        //create transaction and save it
        Transaction transaction=Transaction.builder()
                .transactionType(TransactionType.SALE)
                .transactionStatus(TransactionStatus.COMPLETED)
                .product(product)

                .user(user)
                .totalProducts(quantity)
                .totalprices(product.getPrice().multiply(new java.math.BigDecimal(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction Sold successfully")
                .build();
    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {
        Long productId=  transactionRequest.getProductId();
        Long supplierId=transactionRequest.getSupplierId();
        Integer quantity=transactionRequest.getQuantity();
        if(supplierId==null)throw new NameValueRequiredException("supplierId is required");
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new NameValueRequiredException("product not found"));
        Supplier supplier=supplierRepository.findById(supplierId)
                .orElseThrow(()->new NameValueRequiredException("supplier not found"));
        User user=userService.getCurrentloggedinUser();

        //update stock quentity and re_save
        product.setStockQuentity(product.getStockQuentity().subtract(java.math.BigInteger.valueOf(quantity)));
        productRepository.save(product);



        //create transaction and save it
        Transaction transaction=Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .transactionStatus(TransactionStatus.PROCESSING)
                .product(product)
                .supplier(supplier)
                .user(user)
                .totalProducts(quantity)
                .totalprices(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Transaction ReturnedTo Supplier successfully")
                .build();
    }

    @Override
    public Response getAllTransactons(int page, int size, String searchText) {
        Pageable pageable= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));
        Page<Transaction> transactionsPage=transactionRepository.searchTransactions(searchText,pageable);
        List<TransactionDto>transactionDtos=modelMapper
                .map(transactionsPage.getContent(), new TypeToken<List<TransactionDto>>(){}.getType());


        transactionDtos.forEach(transactionDtoItem ->{
            transactionDtoItem.setUser(null) ;
            transactionDtoItem.setProduct(null);
            transactionDtoItem.setSupplier(null);

                });

        return  Response.builder()
                .status(200)
                .message("success")
                .transactionDtos(transactionDtos)
                .build();


    }

    @Override
    public Response getTransactionById(Long id) {
        Transaction transaction=transactionRepository.findById(id)
                .orElseThrow(()->new NotFoundException("transaction not found"));


        TransactionDto transactionDto=modelMapper.map(transaction,TransactionDto.class);
        transactionDto.getUser().setTransactions(null);//remove user transactoins list

        return  Response.builder()
                .status(200)
                .message("success")
                .transactionDto(transactionDto)
                .build();

    }


    @Override
    public Response getAllTransactonsByMonthAndYear(int month, int year) {
        List<Transaction>transactions=transactionRepository.findAllByMonthAndYear(month,year);
        List<TransactionDto>transactionDtos=modelMapper
                .map(transactions, new TypeToken<List<TransactionDto>>(){}.getType());


        transactionDtos.forEach(transactionDtoItem ->{
            transactionDtoItem.setUser(null) ;
            transactionDtoItem.setProduct(null);
            transactionDtoItem.setSupplier(null);

        });

        return  Response.builder()
                .status(200)
                .message("success")
                .transactionDtos(transactionDtos)
                .build();
    }

    @Override
    public Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus) {
        Transaction exitingTransaction=transactionRepository.findById(transactionId)
                .orElseThrow(()->new NotFoundException("transaction not found"));

        exitingTransaction.setTransactionStatus(transactionStatus);
        exitingTransaction.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(exitingTransaction);




        return  Response.builder()
                .status(200)
                .message("transaction status updated successfully")
                .build();
    }
}
