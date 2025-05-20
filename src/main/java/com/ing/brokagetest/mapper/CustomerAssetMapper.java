package com.ing.brokagetest.mapper;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.entity.CustomerAsset;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerAssetMapper extends BaseMapper<CustomerAssetDTO, CustomerAsset> {
    public CustomerAssetMapper(ModelMapper mapper) {
        super(mapper, CustomerAssetDTO.class, CustomerAsset.class);
    }
}
