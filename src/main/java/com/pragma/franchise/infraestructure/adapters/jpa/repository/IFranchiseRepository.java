package com.pragma.franchise.infraestructure.adapters.jpa.repository;

import com.pragma.franchise.infraestructure.adapters.jpa.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {

}
