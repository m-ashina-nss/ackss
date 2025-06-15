package com.example.demo.controller;

import com.example.demo.dto.CustomerForm;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", repository.findAll());
        return "customers/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("customerForm", new CustomerForm());
        return "customers/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute CustomerForm customerForm, BindingResult result) {
        if (result.hasErrors()) {
            return "customers/form";
        }

        Customer customer = new Customer();
        customer.setId(customerForm.getId()); // 更新時に使用
        customer.setName(customerForm.getName());
        customer.setBirthDate(customerForm.getBirthDate());
        customer.setAddress(customerForm.getAddress());

        repository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Customer customer = repository.findById(id).orElseThrow();
        CustomerForm form = new CustomerForm();
        form.setId(customer.getId());
        form.setName(customer.getName());
        form.setBirthDate(customer.getBirthDate());
        form.setAddress(customer.getAddress());
        model.addAttribute("customerForm", form);
        return "customers/form";
    }
}
