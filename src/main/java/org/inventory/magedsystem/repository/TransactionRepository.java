package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionDto,Long> {

    @Query("SELECT t FROM TransactionDto t " +
            "WHERE YEAR(t.createdAt) = :year AND MONTH(t.createdAt) = :month")
    List<TransactionDto> findAllByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT t FROM TransactionDto t " +
            "LEFT JOIN t.Product p " +
            "WHERE (:searchText IS NULL OR " +
            "LOWER(t.description) LIKE LOWER (CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.transactionStatus) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(p.name) LIKE LOWER (CONCAT('%', :searchText, '%')))")

    Page<TransactionDto>searchTransactions(@Param("searchText")String searchText, Pageable pageable);
}

