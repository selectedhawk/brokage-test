package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.entity.CustomerAsset;
import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.mapper.CustomerAssetMapper;
import com.ing.brokagetest.repository.CustomerAssetRepository;
import com.ing.brokagetest.service.CustomerAssetService;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;

@Service
public class CustomerAssetHandler extends BaseHandler<CustomerAssetDTO, CustomerAsset> implements CustomerAssetService {
    public CustomerAssetHandler(CustomerAssetMapper mapper, CustomerAssetRepository repository) {
        super(mapper, repository);
    }

    @Override
    public void matched(long id, Integer size, EnumOrderSide side) {
        try {
            CustomerAssetDTO dto = getById(id);
            super.update(CalculateHelper.calculateSize(size, side, dto), id);
        } catch (OptimisticLockException e) {
            this.matched(id, size, side);
        }
    }
}
