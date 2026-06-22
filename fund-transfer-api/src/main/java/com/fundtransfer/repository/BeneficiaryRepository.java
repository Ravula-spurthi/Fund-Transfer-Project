package com.fundtransfer.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.Beneficiary;
 
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
 
}
 