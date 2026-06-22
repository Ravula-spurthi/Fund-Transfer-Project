package com.fundtransfer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundtransfer.dto.BeneficiaryDTO;
import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.repository.BeneficiaryRepository;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository repository;

    public Beneficiary addBeneficiary(BeneficiaryDTO dto) {

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setBeneficiaryName(dto.getBeneficiaryName());
        beneficiary.setAccountNumber(dto.getAccountNumber());
        beneficiary.setBankName(dto.getBankName());
        beneficiary.setIfscCode(dto.getIfscCode());
        beneficiary.setUserId(dto.getUserId());

        return repository.save(beneficiary);
    }

    public List<Beneficiary> getBeneficiaries(Long userId) {
        return repository.findByUserId(userId);
    }

    public void deleteBeneficiary(Long id) {
        repository.deleteById(id);
    }
}