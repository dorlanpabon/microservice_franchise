package com.pragma.franchise.infrastructure.adapters.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("franchise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

}
