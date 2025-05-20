package com.ing.brokagetest.controller;

import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.service.UserCustomerOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-customer-order")
public class UserCustomerOrderController extends BaseController<CustomerOrderDTO> {
    public UserCustomerOrderController(UserCustomerOrderService service) {
        super(service);
    }
}
