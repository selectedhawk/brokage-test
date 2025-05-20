package com.ing.brokagetest.mapper;

import com.ing.brokagetest.dto.CustomerDTO;
import com.ing.brokagetest.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper extends BaseMapper<CustomerDTO, Customer> {
    public CustomerMapper(ModelMapper mapper) {
        super(mapper, CustomerDTO.class, Customer.class);
    }
}
