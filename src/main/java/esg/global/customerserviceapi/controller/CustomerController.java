package esg.global.customerserviceapi.controller;

import esg.global.customerserviceapi.domain.Customer;
import esg.global.customerserviceapi.exception.CustomerNotFoundException;
import esg.global.customerserviceapi.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@Slf4j
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<Object> saveCustomer(@Valid @RequestBody Customer customer) {
        Customer result = customerService.saveCustomer(customer);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{ref}")
    public ResponseEntity<Customer> findCustomerByRef(@Valid @PathVariable String ref) throws CustomerNotFoundException {
        Customer customerByRef = customerService.findCustomerByRef(ref);
        return new ResponseEntity<>(customerByRef, HttpStatus.OK);
    }
}
