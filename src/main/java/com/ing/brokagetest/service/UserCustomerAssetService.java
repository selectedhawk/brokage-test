package com.ing.brokagetest.service;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.enums.EnumOrderSide;

public interface UserCustomerAssetService extends BaseService<CustomerAssetDTO> {

    void matched(long id, Integer size, EnumOrderSide side);
}
