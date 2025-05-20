package com.ing.brokagetest.dto;

import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.enums.EnumOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerOrderDTO extends BaseDTO{

    @NotNull
    private Long customerId;
    @NotNull
    private String assetName;
    @NotNull
    private Long assetId;
    @NotNull
    private Integer size;
    @NotNull
    private EnumOrderSide orderSide;
    private Double price;
    private EnumOrderStatus status;
    private Long createDate;

}
