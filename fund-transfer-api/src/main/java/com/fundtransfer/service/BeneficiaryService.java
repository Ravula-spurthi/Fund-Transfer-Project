package com.fundtransfer.service;

import java.util.List;
import java.util.Optional;

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

        // Check duplicate beneficiary
        Optional<Beneficiary> existing =
                repository.findByUserIdAndAccountNumber(
                        dto.getUserId(),
                        dto.getAccountNumber());

        if (existing.isPresent()) {
            throw new RuntimeException("Beneficiary already exists");
        }

        String ifsc = dto.getIfscCode();

if (ifsc == null || !ifsc.matches("^[A-Z]{4}0[A-Z0-9]{6}$")) {
    throw new RuntimeException("Invalid IFSC Code");
}

if(dto.getBranch()==null ||
dto.getBranch().trim().isEmpty()){

throw new RuntimeException("Branch cannot be empty");

}

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setBeneficiaryName(dto.getBeneficiaryName());
        beneficiary.setAccountNumber(dto.getAccountNumber());
        beneficiary.setBankName(dto.getBankName());
        beneficiary.setIfscCode(dto.getIfscCode());
        beneficiary.setBranch(dto.getBranch());
        beneficiary.setUserId(dto.getUserId());

        return repository.save(beneficiary);
    }

    public List<Beneficiary> getBeneficiaries(Long userId) {
        return repository.findByUserId(userId);
    }

public List<Beneficiary> searchBeneficiary(String name){

return repository.findByBeneficiaryNameContainingIgnoreCase(name);

}
    public void deleteBeneficiary(Long id) {
        repository.deleteById(id);
    }
}