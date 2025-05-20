package com.ing.brokagetest.controller;

import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.service.CustomerOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-order")
public class CustomerOrderController extends BaseController<CustomerOrderDTO> {
    public CustomerOrderController(CustomerOrderService service) {
        super(service);
    }
}
