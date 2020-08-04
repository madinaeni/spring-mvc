package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/customer")
@Controller
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/list")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.listAllCustomers());

        return "customer/list";
    }

    @RequestMapping("/show/{id}")
    public String showCustomer(@PathVariable Integer id,Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));

        return "customer/show";
    }

    @RequestMapping("/new")
    public String newCustomer(Model model) {

        model.addAttribute("customer", new Customer());

        return "customer/customerform";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdateCustomer(Customer customer) {
        customerService.saveOrUpdateCustomer(customer);

        return "redirect:/customer/show/" + customer.getId();
    }

    @RequestMapping("/update/{id}")
    public String updateCustomer(@PathVariable Integer id, Model model) {

        model.addAttribute("customer", customerService.getCustomerById(id));

        return "customer/customerform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);

        return "redirect:/customer/list";
    }
}
