package esg.global.customerserviceapi.repository;

import esg.global.customerserviceapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByCustomerRef(String customerRef);
}
