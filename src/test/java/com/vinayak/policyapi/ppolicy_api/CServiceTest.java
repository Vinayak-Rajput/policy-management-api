package com.vinayak.policyapi.ppolicy_api;

import com.vinayak.policyapi.ppolicy_api.models.Customer;
import com.vinayak.policyapi.ppolicy_api.dataTransferObjects.CustomerRequestDTO;
import com.vinayak.policyapi.ppolicy_api.repository.CustomerRepo;
import com.vinayak.policyapi.ppolicy_api.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CServiceTest {

    @Mock
    private CustomerRepo cRepo;

    @InjectMocks
    private CustomerService cService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer_Success() {

        CustomerRequestDTO reqDTO = new CustomerRequestDTO();
        reqDTO.setName("Vinayak Rajput");
        reqDTO.setAge(22);
        reqDTO.setPanNumber("ABCDE1234F");
        reqDTO.setNomineeName("Rahul Rajput");

        Customer savedCustomer = new Customer();
        savedCustomer.setCustomerId(1);
        savedCustomer.setCustomerName("Vinayak Rajput");
        
        when(cRepo.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = cService.createCustomer(reqDTO);

        assertNotNull(result);
        assertEquals(1, result.getCustomerId());
        assertEquals("Vinayak Rajput", result.getCustomerName());
    }

    @Test
    void testCreateCustomer_FailsWhenNomineeIsCustomer() {

        CustomerRequestDTO reqDTO = new CustomerRequestDTO();
        reqDTO.setName("Vinayak Rajput");
        reqDTO.setNomineeName("Vinayak Rajput");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cService.createCustomer(reqDTO);
        });

        assertEquals("Business Rule Violation: A customer cannot be their own nominee.", exception.getMessage());
    }
}