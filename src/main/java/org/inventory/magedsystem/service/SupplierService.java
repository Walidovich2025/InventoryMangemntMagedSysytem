package org.inventory.magedsystem.service;

import org.inventory.magedsystem.dto.CategoryDto;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.SupplierDto;

public interface SupplierService {
    Response addSupplier(SupplierDto supplierDto);
    Response updateSupplier(Long id, SupplierDto supplierDto);

    Response getAllSuppliers();
    Response getSupplierById(Long id);
    Response deleteSupplier(Long id);

}
