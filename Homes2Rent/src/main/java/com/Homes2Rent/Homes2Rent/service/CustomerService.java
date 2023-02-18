package com.Homes2Rent.Homes2Rent.service;


import com.Homes2Rent.Homes2Rent.dto.CustomerDto;
import com.Homes2Rent.Homes2Rent.dto.CustomerInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Customer;
import com.Homes2Rent.Homes2Rent.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService  {

    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CustomerInputDto dto) {
        if(customerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicatedEntryException("customer email already exists");
        }else {
            Customer customer = transferToCustomer(dto);
            customerRepository.save(customer);

            return transferToDto(customer);
        }
    }

    public CustomerDto updateCustomer(Long id, CustomerInputDto dto) {
        if (customerRepository.findById(id).isPresent()) {
            Customer customer = customerRepository.findById(id).get();

            Customer existingCustomer = customerRepository.findCustomerByEmail(dto.getEmail());

            if(existingCustomer != null && !existingCustomer.getId().equals(customer.getId())) {
                throw new DuplicatedEntryException("Email already exists");
            } else {
                Customer updatedCustomer = transferToCustomer(dto);
                updatedCustomer.setId(customer.getId());

                customerRepository.save(updatedCustomer);

                return transferToDto(updatedCustomer);
            }
        } else {
            throw new RecordNotFoundException("No customer found");
        }
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtolist = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDto dto = transferToDto(customer);
            customerDtolist.add(dto);
        }
        return customerDtolist;
    }

    public CustomerDto getOneCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer c = customer.get();
            return transferToDto(c);
        } else {
            throw new RecordNotFoundException("Customer not found");
        }
    }

    public CustomerDto transferToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setEmail(customer.getEmail());
        dto.setFirstname(customer.getFirstname());
        dto.setLastname(customer.getLastname());
        dto.setTown(customer.getTown());
        dto.setStreetname(customer.getStreetname());
        dto.setZipcode(customer.getZipcode());

        return dto;
    }

    public Customer transferToCustomer(CustomerInputDto dto) {
        Customer customer = new Customer();
        customer.setEmail(dto.email);
        customer.setFirstname(dto.firstname);
        customer.setLastname(dto.lastname);
        customer.setTown(dto.town);
        customer.setStreetname(dto.streetname);
        customer.setZipcode(dto.zipcode);

        return customer;
    }
}

