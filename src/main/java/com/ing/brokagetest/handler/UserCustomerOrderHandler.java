package com.ing.brokagetest.handler;

import com.ing.brokagetest.configuration.HeaderConfig;
import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.entity.CustomerOrder;
import com.ing.brokagetest.enums.EnumOrderStatus;
import com.ing.brokagetest.mapper.CustomerOrderMapper;
import com.ing.brokagetest.repository.CustomerOrderRepository;
import com.ing.brokagetest.service.UserCustomerAssetService;
import com.ing.brokagetest.service.UserCustomerOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCustomerOrderHandler extends BaseHandler<CustomerOrderDTO, CustomerOrder> implements UserCustomerOrderService {
    private final UserCustomerAssetService customerAssetService;
    private final HeaderConfig headerConfig;
    public UserCustomerOrderHandler(CustomerOrderMapper mapper, CustomerOrderRepository repository,
                                    UserCustomerAssetService customerAssetService, HeaderConfig headerConfig) {
        super(mapper, repository);
        this.customerAssetService = customerAssetService;
        this.headerConfig = headerConfig;
    }

    @Transactional
    @Override
    public CustomerOrderDTO create(CustomerOrderDTO dto) {
        dto.setStatus(EnumOrderStatus.PENDING);
        CustomerAssetDTO assetDTO = customerAssetService.getById(dto.getAssetId());
        if (!assetDTO.getCustomerId().equals(headerConfig.getCustomerId())) throw new IllegalArgumentException("Asset Not Found!");
        dto.setPrice(assetDTO.getPrice() * dto.getSize());
        dto.setCustomerId(headerConfig.getCustomerId());
        return super.create(dto);
    }

    @Override
    public CustomerOrderDTO update(CustomerOrderDTO dto, long id) {
        CustomerOrderDTO oldDTO = getById(id);
        if (!oldDTO.getStatus().equals(EnumOrderStatus.PENDING)) return oldDTO;
        if (dto.getStatus().equals(EnumOrderStatus.MATCHED))
            customerAssetService.matched(dto.getAssetId(), dto.getSize(), dto.getOrderSide());
        oldDTO.setStatus(dto.getStatus());
        return super.update(oldDTO, id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        CustomerOrderDTO dto = getById(id);
        if (dto.getStatus() != EnumOrderStatus.PENDING) throw new IllegalArgumentException("This Order is not PENDING");
        super.delete(id);
    }

    @Override
    public CustomerOrderDTO getById(long id) {
        CustomerOrderDTO dto = super.getById(id);
        if (!dto.getCustomerId().equals(headerConfig.getCustomerId())) throw new IllegalArgumentException("Record Not Found!");
        return dto;
    }
}
