package com.ing.brokagetest.handler;

import com.ing.brokagetest.configuration.HeaderConfig;
import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.entity.CustomerAsset;
import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.mapper.CustomerAssetMapper;
import com.ing.brokagetest.repository.CustomerAssetRepository;
import com.ing.brokagetest.service.UserCustomerAssetService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCustomerAssetHandler extends BaseHandler<CustomerAssetDTO, CustomerAsset> implements UserCustomerAssetService {
    private final HeaderConfig headerConfig;

    public UserCustomerAssetHandler(CustomerAssetMapper mapper, CustomerAssetRepository repository, HeaderConfig headerConfig) {
        super(mapper, repository);
        this.headerConfig = headerConfig;
    }

    @Override
    public CustomerAssetDTO getById(long id) {
        CustomerAssetDTO dto = super.getById(id);
        if (!dto.getCustomerId().equals(headerConfig.getCustomerId())) throw new IllegalArgumentException("Record Not Found!");
        return dto;
    }

    @Override
    public List<CustomerAssetDTO> list(CustomerAssetDTO dto, Long startDate, Long endDate, Integer page, Integer size) {
        dto.setCustomerId(headerConfig.getCustomerId());
        return super.list(dto, startDate, endDate, page, size);
    }

    @Override
    public CustomerAssetDTO update(CustomerAssetDTO dto, long id) {
        getById(id);
        return super.update(dto, id);
    }

    @Override
    public CustomerAssetDTO create(CustomerAssetDTO dto) {
        dto.setCustomerId(headerConfig.getCustomerId());
        return super.create(dto);
    }

    @Override
    public void delete(long id) {
        getById(id);
        super.delete(id);
    }

    @Override
    public void matched(long id, Integer size, EnumOrderSide side) {
        try {
            CustomerAssetDTO dto = getById(id);
            update(CalculateHelper.calculateSize(size, side, dto), id);
        } catch (OptimisticLockException e) {
            this.matched(id, size, side);
        }
    }

}
