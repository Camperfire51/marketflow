package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
