package guru.springframework.services;

import guru.springframework.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    public List<Customer> listAllCustomers();

    Customer getCustomerById(Integer id);

    Customer saveOrUpdateCustomer(Customer customer);

    void deleteCustomer(Integer id);
}
