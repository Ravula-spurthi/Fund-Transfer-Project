package com.fundtransfer.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fundtransfer.dto.BeneficiaryDTO;
import com.fundtransfer.dto.ChangeTransactionPinDTO;
import com.fundtransfer.dto.ForgotPasswordDTO;
import com.fundtransfer.dto.LoginResponseDTO;
import com.fundtransfer.dto.RegisterDTO;
import com.fundtransfer.dto.SetTransactionPinDTO;
import com.fundtransfer.entity.Beneficiary;
import com.fundtransfer.entity.User;
import com.fundtransfer.repository.BeneficiaryRepository;
import com.fundtransfer.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ServiceLayerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BeneficiaryRepository beneficiaryRepository;

    @Mock
    private TransactionPinService transactionPinService;

    @InjectMocks
    private AuthService authService;

    @InjectMocks
    private BeneficiaryService beneficiaryService;

    @InjectMocks
    private DashboardService dashboardService;

    @InjectMocks
    private TransactionPinService transactionPinServiceImpl;

    @Test
    void loginReturnsUserNotFoundWhenEmailDoesNotExist() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        Object result = authService.login("missing@example.com", "password");

        assertEquals("User Not Found", result);
    }

    @Test
    void loginReturnsInvalidPasswordWhenPasswordMismatch() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("correct");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        Object result = authService.login("user@example.com", "wrong");

        assertEquals("Invalid Password", result);
    }

    @Test
    void loginReturnsLoginResponseForValidCredentials() {
        User user = new User();
        user.setId(10L);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setAccountNumber("1000000001");
        user.setPassword("secret");
        user.setBalance(2500.0);

        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));

        Object result = authService.login("alice@example.com", "secret");

        assertTrue(result instanceof LoginResponseDTO);
        LoginResponseDTO response = (LoginResponseDTO) result;
        assertEquals(10L, response.getId());
        assertEquals("Alice", response.getName());
        assertEquals(2500.0, response.getBalance());
    }

    @Test
    void forgotPasswordUpdatesPasswordSuccessfully() {
        ForgotPasswordDTO dto = new ForgotPasswordDTO();
        dto.setEmail("user@example.com");
        dto.setMobile("9999999999");
        dto.setNewPassword("newSecret");

        User user = new User();
        user.setPassword("oldPassword");
        when(userRepository.findByEmailAndMobile("user@example.com", "9999999999")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = authService.forgotPassword(dto);

        assertEquals("Password Updated Successfully", result);
        assertEquals("newSecret", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void registerRejectsDuplicateEmail() {
        RegisterDTO dto = new RegisterDTO();
        dto.setEmail("dup@example.com");
        dto.setAccountNumber("1000000002");
        dto.setMobile("8888888888");

        when(userRepository.findByEmail("dup@example.com")).thenReturn(Optional.of(new User()));

        String result = authService.register(dto);

        assertEquals("Email already exists", result);
    }

    @Test
    void addBeneficiarySavesValidBeneficiary() {
        BeneficiaryDTO dto = new BeneficiaryDTO();
        dto.setUserId(1L);
        dto.setBeneficiaryName("Bob");
        dto.setAccountNumber("1234567890");
        dto.setIfscCode("ABCD0123456");
        dto.setBankName("Test Bank");
        dto.setBranch("Main Branch");
        dto.setMobileNumber("7777777777");

        when(beneficiaryRepository.findByUserIdAndAccountNumber(1L, "1234567890")).thenReturn(Optional.empty());
        when(beneficiaryRepository.save(any(Beneficiary.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Beneficiary result = beneficiaryService.addBeneficiary(dto);

        assertNotNull(result);
        assertEquals("Bob", result.getBeneficiaryName());
        assertEquals("1234567890", result.getAccountNumber());
        verify(beneficiaryRepository).save(any(Beneficiary.class));
    }

    @Test
    void addBeneficiaryRejectsInvalidIfscCode() {
        BeneficiaryDTO dto = new BeneficiaryDTO();
        dto.setUserId(1L);
        dto.setBeneficiaryName("Bob");
        dto.setAccountNumber("1234567890");
        dto.setIfscCode("bad-ifsc");
        dto.setBankName("Test Bank");
        dto.setBranch("Main Branch");
        dto.setMobileNumber("7777777777");

        when(beneficiaryRepository.findByUserIdAndAccountNumber(1L, "1234567890")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> beneficiaryService.addBeneficiary(dto));
        assertEquals("Invalid IFSC Code", exception.getMessage());
    }

    @Test
    void getDashboardReturnsUserDetails() {
        User user = new User();
        user.setName("Carol");
        user.setAccountNumber("2000000001");
        user.setBalance(4200.0);
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));

        var result = dashboardService.getDashboard(5L);

        assertEquals("Carol", result.getName());
        assertEquals("2000000001", result.getAccountNumber());
        assertEquals(4200.0, result.getBalance());
    }

    @Test
    void setPinCreatesTransactionPinSuccessfully() {
        User user = new User();
        user.setPinCreated(false);
        SetTransactionPinDTO dto = new SetTransactionPinDTO();
        dto.setUserId(7L);
        dto.setNewPin("1234");

        when(userRepository.findById(7L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = transactionPinServiceImpl.setPin(dto);

        assertEquals("Transaction PIN Created Successfully", result);
        assertEquals("1234", user.getTransactionPin());
        assertTrue(user.getPinCreated());
    }

    @Test
    void changePinRejectsIncorrectOldPin() {
        User user = new User();
        user.setTransactionPin("1111");
        ChangeTransactionPinDTO dto = new ChangeTransactionPinDTO();
        dto.setUserId(8L);
        dto.setOldPin("0000");
        dto.setNewPin("2222");

        when(userRepository.findById(8L)).thenReturn(Optional.of(user));

        String result = transactionPinServiceImpl.changePin(dto);

        assertEquals("Old PIN is incorrect", result);
    }

    @Test
    void getBeneficiariesAndSearchReturnRepositoryResults() {
        Beneficiary b1 = new Beneficiary();
        b1.setBeneficiaryName("Bob");
        when(beneficiaryRepository.findByUserId(1L)).thenReturn(List.of(b1));
        when(beneficiaryRepository.findByBeneficiaryNameContainingIgnoreCase("bob")).thenReturn(List.of(b1));

        List<Beneficiary> byUser = beneficiaryService.getBeneficiaries(1L);
        List<Beneficiary> byName = beneficiaryService.searchBeneficiary("bob");

        assertEquals(1, byUser.size());
        assertEquals(1, byName.size());
        assertEquals("Bob", byName.get(0).getBeneficiaryName());
    }
}
