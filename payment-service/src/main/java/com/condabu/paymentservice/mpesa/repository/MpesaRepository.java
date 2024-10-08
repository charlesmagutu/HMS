package com.condabu.paymentservice.mpesa.repository;

import com.condabu.paymentservice.mpesa.model.MpesaTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MpesaRepository extends JpaRepository<MpesaTransaction, Long> {
}
