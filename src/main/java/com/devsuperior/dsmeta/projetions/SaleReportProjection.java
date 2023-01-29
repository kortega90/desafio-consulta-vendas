package com.devsuperior.dsmeta.projetions;

import java.time.LocalDate;

public interface SaleReportProjection {
    String id();
    LocalDate date();
    Double amount();
   String sellerName();

}
