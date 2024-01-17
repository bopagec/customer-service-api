package esg.global.customerserviceapi.service;

import esg.global.customerserviceapi.domain.Customer;
import esg.global.customerserviceapi.exception.CustomerNotFoundException;
import esg.global.customerserviceapi.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Customer saveCustomer(Customer customer){
        return repository.save(customer);
    }

    public Customer findCustomerByRef(String customerRef) throws CustomerNotFoundException {
        Optional<Customer> optCustomer = repository.findByCustomerRef(customerRef);

        Customer customer = optCustomer.orElseThrow(() -> {
            log.error("customer not found for ref: {}", customerRef);
            return new CustomerNotFoundException("customer not found");
        });

        return customer;
    }
}
