package com.server.api.ebank.controller;

import com.server.api.ebank.dto.CustomerDto;
import com.server.api.ebank.entity.Customer;
import com.server.api.ebank.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Opérations liées aux clients")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{cin}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Récupère un client par CIN")
    public ResponseEntity<CustomerDto> findCustomerByCin(@PathVariable String cin) {
        return ResponseEntity.ok(customerService.findByCIN(cin));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Créer un nouveau client")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.createCustomer(customerDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Met à jour un client existant")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Integer id, @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Récupère tous les clients")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Supprime un client par ID")
    public ResponseEntity<Customer> deleteCustomerById(@PathVariable("id") Integer id) {
        this.customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
