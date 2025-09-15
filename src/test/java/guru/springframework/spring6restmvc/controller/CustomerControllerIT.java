package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;
    BeerRepository beerRepository;

    @Test
    void testBeerIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void listAllCustomers() {
        List<CustomerDTO> customers = customerController.listAllCustomers();
        assert customers.size() == 3;
    }

    @Test
    void getCustomerById() {
        Customer c = customerRepository.findAll().get(0);
        CustomerDTO customer = customerController.getCustomerById(c.getId());
        assertNotNull(customer);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.listAllCustomers();
        assertThat(customers.size()).isEqualTo(0);
    }

}