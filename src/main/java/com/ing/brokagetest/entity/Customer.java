package com.ing.brokagetest.entity;

import com.ing.brokagetest.enums.EnumCustomerType;
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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Customer extends BaseEntity {
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private EnumCustomerType customerType;
}
