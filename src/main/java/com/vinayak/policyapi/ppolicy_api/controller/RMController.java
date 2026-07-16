package com.vinayak.policyapi.ppolicy_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reference-master")
public class RMController {

    @GetMapping("/{category}")
    public ResponseEntity<?> getReferenceData(@PathVariable String category) {
        if (category.equalsIgnoreCase("policy-terms")) {
            return new ResponseEntity<>(List.of(10, 15, 20, 25, 30), HttpStatus.OK);
        } else if (category.equalsIgnoreCase("payment-frequencies")) {
            return new ResponseEntity<>(List.of("MONTHLY", "QUARTERLY", "HALF_YEARLY", "YEARLY"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid category. Use 'policy-terms' or 'payment-frequencies'", HttpStatus.BAD_REQUEST);
        }
    }
}