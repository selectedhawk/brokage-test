package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.enums.EnumOrderSide;

public class CalculateHelper {

    public static CustomerAssetDTO calculateSize(Integer size, EnumOrderSide side, CustomerAssetDTO dto) {
        if (side == EnumOrderSide.BUY) {
            dto.setUsableSize(dto.getUsableSize() + size);
            dto.setSize(dto.getSize() + size);
        } else dto.setUsableSize(dto.getUsableSize() - size);
        return dto;
    }
}
