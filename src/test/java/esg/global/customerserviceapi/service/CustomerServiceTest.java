package esg.global.customerserviceapi.service;

import esg.global.customerserviceapi.domain.Customer;
import esg.global.customerserviceapi.exception.CustomerNotFoundException;
import esg.global.customerserviceapi.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CustomerService.class)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository repository;
    private Customer customer;

    @BeforeEach
    public void init(){
        customer = new Customer(
                1l,
                "ref-jd123",
                "John Deen",
                "No: 21",
                "Whitton Road",
                "Whitton",
                "London",
                "UK",
                "TW2 7AB");
    }
    @Test
    @DisplayName("given customer when save returns saved customer")
    public void givenCustomer_whenSave_returnsSavedCustomer(){
        // given customer
        // when
        Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(customer);
        // then
        Customer actual = customerService.saveCustomer(customer);
        Assertions.assertThat(actual).isEqualTo(customer);
    }

    @Test
    @DisplayName("given customer reference when findByRef then returns correct customer")
    public void givenCustomerRef_whenFindCustomerByRef_returnsCorrectCustomer() throws CustomerNotFoundException {
        // given
        String ref = customer.getCustomerRef();
        // when
        Mockito.when(repository.findByCustomerRef(customer.getCustomerRef())).thenReturn(Optional.of(customer));
        // then
        Customer actual = customerService.findCustomerByRef(ref);
        Assertions.assertThat(actual).isEqualTo(customer);
    }

    @Test
    @DisplayName("given customer wrong reference when findByRef then throws CustomerNotFoundException")
    public void givenWrongCustomerRef_whenFindCustomerByRef_throwsCustomerNotFoundException() throws CustomerNotFoundException {
        // given customer
        String ref = "abc123";
        // when
        Mockito.when(repository.findByCustomerRef(customer.getCustomerRef())).thenReturn(Optional.of(customer));
        // then
        CustomerNotFoundException customerNotFoundException = assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerByRef(ref));
        Assertions.assertThat(customerNotFoundException.getMessage()).isEqualTo("customer not found");
    }
}