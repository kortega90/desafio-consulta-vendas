package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummerDTO;
import com.devsuperior.dsmeta.projetions.SaleReportProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;


@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSummerDTO> findSumary(String min, String max, Pageable pageable) {

		LocalDate maxDate = ValidationMaXDate(max);
		LocalDate minDate = ValidationMinDate(maxDate,min);

		 return repository.searchSumary(minDate,maxDate,pageable);
	}
	/*public Page<SaleReportDTO> findReport(Pageable pageable) {
		List<SaleReportProjection> list = repository.searchSumary2();
		List<SaleReportDTO> list2 = list.stream().map(x -> new SaleReportDTO(x)).collect(Collectors.toList());
		Page<SaleReportDTO> result = new PageImpl<SaleReportDTO>(list2,pageable, list2.size());
		return	result;
	}*/
	public Page<SaleReportDTO> findReport(String min, String max,String name, Pageable pageable) {

		LocalDate maxDate = ValidationMaXDate(max);
		LocalDate minDate = ValidationMinDate(maxDate,min);

		Page<SaleReportDTO> result = repository.searchReport(minDate,maxDate,name,pageable);
		return	result;
	}
	private LocalDate ValidationMaXDate (String max){
		LocalDate maxDate;
		if(max.equals("")){
			return  maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else{
			return maxDate = LocalDate.parse(max);
		}
	}
	private LocalDate ValidationMinDate (LocalDate maxDate,String min){
		LocalDate minDate;
		if(min.equals("")){
			return minDate = maxDate.minusYears(1L);

		}
		else {
			return minDate = LocalDate.parse(min);
		}
	}
}


