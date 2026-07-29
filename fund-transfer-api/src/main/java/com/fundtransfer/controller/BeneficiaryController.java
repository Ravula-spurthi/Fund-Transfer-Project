package com.fundtransfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.BeneficiaryDTO;
import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.service.BeneficiaryService;

@RestController
@CrossOrigin(origins = "*")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    // Get beneficiaries of logged in user
    @GetMapping("/beneficiaries/user/{userId}")
    public List<Beneficiary> getAllBeneficiaries(@PathVariable Long userId) {
        return beneficiaryService.getBeneficiaries(userId);
    }

@GetMapping("/beneficiaries/{id}")
public Beneficiary getBeneficiaryById(@PathVariable Long id) {
    return beneficiaryService.getBeneficiaryById(id);
}
    // Search beneficiary
    @GetMapping("/beneficiaries/search")
public List<Beneficiary> searchBeneficiary(
        @RequestParam Long userId,
        @RequestParam String name) {

    return beneficiaryService.searchBeneficiary(userId, name);
}

    // Add beneficiary
    @PostMapping("/beneficiaries")
    public Beneficiary addBeneficiary(@RequestBody BeneficiaryDTO dto) {
        return beneficiaryService.addBeneficiary(dto);
    }

    // Delete beneficiary
    @DeleteMapping("/beneficiaries/{id}")
    public void deleteBeneficiary(@PathVariable Long id) {
        beneficiaryService.deleteBeneficiary(id);
    }

    @PutMapping("/beneficiaries/{id}")
public Beneficiary updateBeneficiary(
        @PathVariable Long id,
        @RequestBody BeneficiaryDTO dto) {

    return beneficiaryService.updateBeneficiary(id, dto);
}

}