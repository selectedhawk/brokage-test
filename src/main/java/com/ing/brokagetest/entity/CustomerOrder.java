package com.ing.brokagetest.entity;

import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.enums.EnumOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {@Index(columnList = "customerId"), @Index(columnList = "status"),
        @Index(columnList = "orderSide"), @Index(columnList = "assetId"), @Index(columnList = "assetName")})
public class CustomerOrder extends BaseEntity {
    private Long customerId;

    private String assetName;

    private Long assetId;

    private Integer size;

    @Enumerated(EnumType.STRING)
    private EnumOrderSide orderSide;

    private Double price;

    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;

}
