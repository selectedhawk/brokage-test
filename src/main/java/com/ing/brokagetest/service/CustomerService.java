package com.ing.brokagetest.service;

import com.ing.brokagetest.dto.CustomerDTO;

public interface CustomerService extends BaseService<CustomerDTO> {

    CustomerDTO findByNameAndPassword(String name, String password);
}
