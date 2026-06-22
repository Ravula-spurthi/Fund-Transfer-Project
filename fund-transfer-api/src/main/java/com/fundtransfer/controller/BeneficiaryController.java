package com.fundtransfer.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.repository.BeneficiaryRepository;
 
@RestController
public class BeneficiaryController {
 
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
 
    @GetMapping("/beneficiaries")
    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }
}