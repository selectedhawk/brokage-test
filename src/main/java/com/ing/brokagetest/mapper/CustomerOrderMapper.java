package com.ing.brokagetest.mapper;

import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.entity.CustomerOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerOrderMapper extends BaseMapper<CustomerOrderDTO, CustomerOrder> {
    public CustomerOrderMapper(ModelMapper mapper) {
        super(mapper, CustomerOrderDTO.class, CustomerOrder.class);
    }
}
