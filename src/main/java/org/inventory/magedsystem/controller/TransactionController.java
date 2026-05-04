package org.inventory.magedsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.TransactionManagement;
import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.TransactionRequest;
import org.inventory.magedsystem.enums.TransactionStatus;
import org.inventory.magedsystem.service.CategoryService;
import org.inventory.magedsystem.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Response> purchase(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.restockInventory(transactionRequest));

    }

    @PostMapping("/sell")
    public ResponseEntity<Response> sell(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.sell( transactionRequest));

    }
    @PostMapping("/return")
    public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.ok(transactionService.returnToSupplier( transactionRequest));

    }

    @GetMapping("/all")
    public  ResponseEntity<Response> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,//extract query paramter from url into method paramter
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(transactionService.getAllTransactons(page,size,searchText));

    }
    @GetMapping("/{id}")
    public  ResponseEntity<Response> getAllTransactionByid(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/month-year")
    public  ResponseEntity<Response> getAllTransactonsByMonthAndYear(
            @RequestParam int month,
            @RequestParam int year
    ){
        return ResponseEntity.ok(transactionService.getAllTransactonsByMonthAndYear(month,year));
    }



    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Response> updateTransactionStatus(@PathVariable Long transactionId,@RequestBody @Valid TransactionStatus transactionStatus) {
         return ResponseEntity.ok(transactionService.updateTransactionStatus(transactionId,transactionStatus));

    }

//






}
