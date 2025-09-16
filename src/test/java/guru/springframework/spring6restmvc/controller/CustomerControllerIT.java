package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper  customerMapper;

    @Test
    void deleteByIdNotFound(){
        assertThrows(NotFoundException.class,()->{
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }


    @Test
    @Rollback
    @Transactional
    void deleteById(){
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity re = customerController.deleteCustomerById(customer.getId());
        assertThat(re.getStatusCode().equals(HttpStatusCode.valueOf(204)));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void updateByIdNotFound(){
        assertThrows(NotFoundException.class,()->{
            customerController.updateCustomerByID(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    @Rollback
    @Transactional
    void updateById(){
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String cusName = "UPDATED";
        customerDTO.setName(cusName);
        ResponseEntity re = customerController.updateCustomerByID(customer.getId(), customerDTO);
        assertThat(re.getStatusCode().equals(HttpStatusCode.valueOf(204)));
        Customer c = customerRepository.findById(customer.getId()).orElse(null);
        assertThat(customer.getName()).isEqualTo(cusName);
    }

    @Test
    @Rollback
    @Transactional
    void saveNewCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder().name("NEW").build();
        ResponseEntity re = customerController.handlePost(customerDTO);
        assertThat(re.getStatusCode().equals(HttpStatusCode.valueOf(204)));
        assertThat(re.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = re.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer c = customerRepository.findById(savedUUID).get();
        assertThat(c).isNotNull();
    }


    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}










