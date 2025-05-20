package com.ing.brokagetest.dto;

import com.ing.brokagetest.enums.EnumCustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO extends BaseDTO{
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private EnumCustomerType customerType;
}
