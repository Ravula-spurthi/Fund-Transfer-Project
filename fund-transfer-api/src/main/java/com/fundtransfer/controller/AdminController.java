package com.fundtransfer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundtransfer.dto.LoginDTO;
import com.fundtransfer.dto.UserBeneficiaryDTO;
import com.fundtransfer.entity.Admin;
import com.fundtransfer.entity.User;
import com.fundtransfer.service.AdminService;
import com.fundtransfer.service.BeneficiaryService;
import com.fundtransfer.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private BeneficiaryService beneficiaryService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users-with-beneficiaries")
    public ResponseEntity<List<UserBeneficiaryDTO>> getAllUsersWithBeneficiaries() {
        List<User> users = userService.getAllUsers();
        List<UserBeneficiaryDTO> result = new java.util.ArrayList<>();

        for (User user : users) {
            UserBeneficiaryDTO dto = new UserBeneficiaryDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setMobile(user.getMobile());
            dto.setAccountNumber(user.getAccountNumber());
            dto.setBeneficiaries(beneficiaryService.getBeneficiaries(user.getId()));
            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
public ResponseEntity<String> deleteUser(@PathVariable Long id) {

    userService.deleteUser(id);

    return ResponseEntity.ok("User deleted successfully");
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Admin> admin = adminService.login(
                loginDTO.getEmail(),
                loginDTO.getPassword());

        if (admin.isPresent()) {
            return ResponseEntity.ok(admin.get());
        }

        return ResponseEntity.badRequest().body("Invalid Admin Credentials");
    }
}