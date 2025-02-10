package com.pragma.franchise.infrastructure.adapters.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("branch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("franchise_id")
    private Long franchiseId;

}
