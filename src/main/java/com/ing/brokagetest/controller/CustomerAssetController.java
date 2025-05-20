package com.ing.brokagetest.controller;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.service.CustomerAssetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-asset")
public class CustomerAssetController extends BaseController<CustomerAssetDTO> {
    public CustomerAssetController(CustomerAssetService service) {
        super(service);
    }
}
