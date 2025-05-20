package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.entity.CustomerOrder;
import com.ing.brokagetest.enums.EnumOrderStatus;
import com.ing.brokagetest.mapper.CustomerOrderMapper;
import com.ing.brokagetest.repository.CustomerOrderRepository;
import com.ing.brokagetest.service.CustomerAssetService;
import com.ing.brokagetest.service.CustomerOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerOrderHandler extends BaseHandler<CustomerOrderDTO, CustomerOrder> implements CustomerOrderService {
    private final CustomerAssetService customerAssetService;

    public CustomerOrderHandler(CustomerOrderMapper mapper, CustomerOrderRepository repository, CustomerAssetService customerAssetService) {
        super(mapper, repository);
        this.customerAssetService = customerAssetService;
    }

    @Transactional
    @Override
    public CustomerOrderDTO create(CustomerOrderDTO dto) {
        dto.setStatus(EnumOrderStatus.PENDING);
        CustomerAssetDTO assetDTO = customerAssetService.getById(dto.getAssetId());
        dto.setPrice(assetDTO.getPrice() * dto.getSize());
        return super.create(dto);
    }

    @Override
    public CustomerOrderDTO update(CustomerOrderDTO dto, long id) {
        CustomerOrderDTO oldDTO = super.getById(id);
        if (!oldDTO.getStatus().equals(EnumOrderStatus.PENDING)) return oldDTO;
        if (dto.getStatus().equals(EnumOrderStatus.MATCHED))
            customerAssetService.matched(dto.getAssetId(), dto.getSize(), dto.getOrderSide());
        oldDTO.setStatus(dto.getStatus());
        return super.update(oldDTO, id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        CustomerOrderDTO dto = super.getById(id);
        if (dto.getStatus() != EnumOrderStatus.PENDING) throw new IllegalArgumentException("This Order is not PENDING");
        super.delete(id);
    }
}
