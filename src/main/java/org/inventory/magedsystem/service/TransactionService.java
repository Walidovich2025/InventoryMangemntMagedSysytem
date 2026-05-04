package org.inventory.magedsystem.service;

import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.TransactionRequest;
import org.inventory.magedsystem.enums.TransactionStatus;

public interface TransactionService {
    Response restockInventory(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest tansactionRequest);

    Response getAllTransactons(int page,int size,String searchText);

    Response getTransactionById(Long id);


    Response getAllTransactonsByMonthAndYear(int month,int year );

    Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus);

}
