package com.pragma.franchise.infrastructure.adapters.jpa.repository;

import com.pragma.franchise.infrastructure.adapters.jpa.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {

}
