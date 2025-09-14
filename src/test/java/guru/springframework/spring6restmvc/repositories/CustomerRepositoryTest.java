package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        CustomerMapper  customerMapper = Mappers.getMapper(CustomerMapper.class);
        CustomerDTO customer1 = CustomerDTO.builder()
                .name("Customer 1")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .name("Customer 2")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .name("Customer 3")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Customer c1 = customerMapper.customerDtoToCustomer(customer1);
        Customer c2 = customerMapper.customerDtoToCustomer(customer2);
        Customer c3 = customerMapper.customerDtoToCustomer(customer3);
        Customer savedCustomer1 = customerRepository.save(c1);
        Customer savedCustomer2 = customerRepository.save(c2);
        Customer savedCustomer3 = customerRepository.save(c3);



        assertThat(savedCustomer1.getId()).isNotNull();
        assertThat(savedCustomer2.getId()).isNotNull();
        assertThat(savedCustomer3.getId()).isNotNull();

    }
}