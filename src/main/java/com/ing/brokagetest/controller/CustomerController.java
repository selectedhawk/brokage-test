package com.ing.brokagetest.controller;

import com.ing.brokagetest.dto.CustomerDTO;
import com.ing.brokagetest.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController<CustomerDTO> {
    public CustomerController(CustomerService service) {
        super(service);
    }
}
