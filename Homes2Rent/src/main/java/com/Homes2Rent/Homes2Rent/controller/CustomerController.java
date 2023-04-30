package com.Homes2Rent.Homes2Rent.controller;
import com.Homes2Rent.Homes2Rent.dto.CustomerDto;
import com.Homes2Rent.Homes2Rent.dto.CustomerInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


    @RestController
    public class CustomerController {

        private final CustomerService customerService;

        public CustomerController(CustomerService customerService) {
            this.customerService = customerService;
        }


        @PostMapping("customers")
        public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerInputDto dto) {
            try {
                CustomerDto customerData = customerService.createCustomer(dto);
                return new ResponseEntity<>(customerData, HttpStatus.OK);
            } catch (RecordNotFoundException | DuplicatedEntryException re) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
            }
        }

        @PutMapping("customers/{id}")
        public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerInputDto customerDto) {
            try {
                CustomerDto dto = customerService.updateCustomer(id, customerDto);
                return ResponseEntity.ok().body(dto);
            } catch (RecordNotFoundException | DuplicatedEntryException re) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
            }
        }

        @DeleteMapping("customers/{id}")
        public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
            try {
                customerService.deleteCustomer(id);
                return ResponseEntity.status(HttpStatus.OK).body("Customer deleted");
            } catch (RecordNotFoundException re) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer found");
            }
        }

        @GetMapping("customers")
        public ResponseEntity<List<CustomerDto>> getCustomers() {
            List<CustomerDto> dtos;
            dtos = customerService.getAllCustomers();
            if (!dtos.isEmpty()) {
                return ResponseEntity.ok().body(dtos);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("customers/{id}")
        public ResponseEntity<Object> getOneCustomer(@PathVariable Long id) {
            try {
                CustomerDto customer = customerService.getOneCustomer(id);
                return ResponseEntity.ok().body(customer);
            } catch (RecordNotFoundException re) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
            }
        }
    }

