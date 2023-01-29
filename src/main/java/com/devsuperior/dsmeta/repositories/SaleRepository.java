package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummerDTO;
import com.devsuperior.dsmeta.projetions.SaleReportProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {



    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummerDTO (obj.seller.name , SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name")
    Page<SaleSummerDTO> searchSumary(LocalDate minDate,LocalDate maxDate, Pageable pageable);
    
    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount,obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name,'%'))")
    Page<SaleReportDTO> searchReport(LocalDate minDate,LocalDate maxDate,String name, Pageable pageable);
}
