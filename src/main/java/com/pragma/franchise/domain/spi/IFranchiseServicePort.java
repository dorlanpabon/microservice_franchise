package com.pragma.franchise.domain.spi;

import com.pragma.franchise.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {

    Mono<Void> save(Franchise franchise);

    Mono<Void> updateFranchiseName(Long franchiseId, String newName);

}
