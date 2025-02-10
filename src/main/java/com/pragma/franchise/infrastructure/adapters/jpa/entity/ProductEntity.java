package com.pragma.franchise.infrastructure.adapters.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("branch_id")
    private Long branchId;

    @Column("stock")
    private Integer stock;

    @Transient
    @Column("branch_name")
    private String branchName;
}
