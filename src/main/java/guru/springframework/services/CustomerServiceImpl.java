package guru.springframework.services;

import guru.springframework.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<Integer, Customer> customers = new HashMap<>();

    public CustomerServiceImpl() {
        initializeData();
    }

    @Override
    public List<Customer> listAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customers.get(id);
    }

    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        if( customer != null) {
            if (customer.getId() == null) {
                Integer id = customers.size() + 1;
                customer.setId(id);
                customers.put(id, customer);
            }
            customers.put(customer.getId(), customer);

            return customer;
        } else {
            throw new RuntimeException("Product can't be null");
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
        customers.remove(id);
    }

    private void initializeData() {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("customer1 first");
        customer1.setLastName("customer1 last");
        customer1.setEmail("customer1@mail.com");
        customer1.setPhoneNumber("0219887");
        customers.put(1, customer1);

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("first customer2");
        customer2.setLastName("last customer2");
        customer2.setEmail("customer2@mail.com");
        customer2.setPhoneNumber("0746291572");
        customers.put(2, customer2);

        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setFirstName("customer3 first");
        customer3.setLastName("customer3 last");
        customer3.setEmail("customer3@mail.com");
        customer3.setPhoneNumber("0214446351");
        customers.put(3, customer3);
    }
}
