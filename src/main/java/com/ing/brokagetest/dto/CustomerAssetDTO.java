package com.ing.brokagetest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CustomerAssetDTO extends BaseDTO{
    @NotNull
    private Long customerId;
    @NotNull
    private String assetName;
    @NotNull
    private Integer size;
    @NotNull
    private Integer usableSize;
    @NotNull
    private Integer version;
    @NotNull
    private Double price;
}
