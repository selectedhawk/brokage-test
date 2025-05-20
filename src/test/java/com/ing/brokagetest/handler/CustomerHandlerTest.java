package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerDTO;
import com.ing.brokagetest.entity.Customer;
import com.ing.brokagetest.enums.EnumCustomerType;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.mapper.CustomerMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.repository.CustomerRepository;
import com.ing.brokagetest.service.CustomerService;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CustomerHandlerTest extends BaseHandlerTest<CustomerDTO, Customer, CustomerService> {

    @Mock
    private CustomerRepository repository;

    @Override
    protected CustomerDTO getDTO() {
        CustomerDTO dto = new CustomerDTO();
        dto.setName("Test Customer");
        dto.setPassword("Test password");
        dto.setId(1L);
        dto.setCustomerType(EnumCustomerType.USER);
        dto.setCreateDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        return dto;
    }

    @Override
    protected BaseMapper<CustomerDTO, Customer> getMapper() {
        return new CustomerMapper(new ModelMapper());
    }

    @Override
    protected CustomerService getService() {
        return new CustomerHandler(new CustomerMapper(new ModelMapper()), repository);
    }

    @Override
    protected BaseRepository<Customer> getRepository() {
        return repository;
    }
}
