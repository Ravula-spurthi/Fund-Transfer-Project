package com.fundtransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.BeneficiaryDTO;
import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.service.BeneficiaryService;

@RestController
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @GetMapping("/beneficiaries")
    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryService.getBeneficiaries(1L);
    }

    @PostMapping("/beneficiaries")
    public Beneficiary addBeneficiary(@RequestBody BeneficiaryDTO dto) {
        return beneficiaryService.addBeneficiary(dto);
    }

    @DeleteMapping("/beneficiaries/{id}")
    public void deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id); 
    }
}
