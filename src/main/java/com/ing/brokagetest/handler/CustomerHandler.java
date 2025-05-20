package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerDTO;
import com.ing.brokagetest.entity.Customer;
import com.ing.brokagetest.enums.EnumCustomerType;
import com.ing.brokagetest.mapper.CustomerMapper;
import com.ing.brokagetest.repository.CustomerRepository;
import com.ing.brokagetest.service.CustomerService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class CustomerHandler extends BaseHandler<CustomerDTO, Customer> implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    public CustomerHandler(CustomerMapper mapper, CustomerRepository repository) {
        super(mapper, repository);
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void initAdminUser() {
        if (repository.findByName("admin").isEmpty()) {
            CustomerDTO admin = new CustomerDTO();
            admin.setName("admin");
            admin.setPassword("admin");
            admin.setCustomerType(EnumCustomerType.ADMIN);
            admin.setCreateDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            create(admin);
        }
    }
    @Override
    public CustomerDTO findByNameAndPassword(String name, String password) {
        Customer customer = repository.findByNameAndPassword(name, password);
        return mapper.deserialize(customer);
    }
}
