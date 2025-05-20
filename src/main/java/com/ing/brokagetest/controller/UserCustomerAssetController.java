package com.ing.brokagetest.controller;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.service.UserCustomerAssetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-customer-asset")
public class UserCustomerAssetController extends BaseController<CustomerAssetDTO> {
    public UserCustomerAssetController(UserCustomerAssetService service) {
        super(service);
    }
}
