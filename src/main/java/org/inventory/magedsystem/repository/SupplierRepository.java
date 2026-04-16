package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.SupplierDto;
import org.inventory.magedsystem.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
