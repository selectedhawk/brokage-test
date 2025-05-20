package com.ing.brokagetest.entity;

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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"assetName"})},
        indexes={@Index(columnList = "customerId")})
public class CustomerAsset extends BaseEntity{

    private Long customerId;
    private String assetName;
    private Integer size;
    private Integer usableSize;
    private Double price;
    @Version
    private Integer version;
}
