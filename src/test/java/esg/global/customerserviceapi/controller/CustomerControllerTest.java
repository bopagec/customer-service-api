package esg.global.customerserviceapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import esg.global.customerserviceapi.domain.Customer;
import esg.global.customerserviceapi.service.CustomerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    public void init(){
        customer = Customer.builder()
                .customerRef("ref123")
                .addressLine1("No: 14")
                .addressLine2("London Road")
                .customerName("Jone Deen")
                .county("London")
                .town("Whitton")
                .country("UK")
                .postcode("TW2 7AB")
                .build();
    }


    @Test
    @DisplayName("given customer when save then returns customer with HttpCreated statusCode")
    public void givenCustomer_whenSave_thenReturnCustomerWithHttpCreatedStatusCode() throws Exception {
        // given
        BDDMockito.given(customerService.saveCustomer(ArgumentMatchers.any(Customer.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/customer/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));
        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerRef", CoreMatchers.is(customer.getCustomerRef())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName", CoreMatchers.is(customer.getCustomerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressLine1", CoreMatchers.is(customer.getAddressLine1())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressLine2", CoreMatchers.is(customer.getAddressLine2())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.town", CoreMatchers.is(customer.getTown())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.county", CoreMatchers.is(customer.getCounty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(customer.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postcode", CoreMatchers.is(customer.getPostcode())));

    }

    @Test
    @DisplayName("given customer ref when find customerByRef then returns correct customer")
    public void givenCustomerRef_whenFindCustomerByRef_thenReturnsCorrectCustomer() throws Exception {
        // given
        BDDMockito.given(customerService.findCustomerByRef(customer.getCustomerRef()))
                .willReturn(customer);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/customer/{ref}", customer.getCustomerRef()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerRef", CoreMatchers.is(customer.getCustomerRef())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerName", CoreMatchers.is(customer.getCustomerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressLine1", CoreMatchers.is(customer.getAddressLine1())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressLine2", CoreMatchers.is(customer.getAddressLine2())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.town", CoreMatchers.is(customer.getTown())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.county", CoreMatchers.is(customer.getCounty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", CoreMatchers.is(customer.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postcode", CoreMatchers.is(customer.getPostcode())));
    }

}