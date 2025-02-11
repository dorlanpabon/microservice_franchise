package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.model.Branch;
import com.pragma.franchise.domain.spi.IBranchServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.BranchRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IBranchRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;

import static org.mockito.Mockito.*;

class BranchHandlerTest {

    @Mock
    private IBranchServicePort branchServicePort;

    @Mock
    private IBranchRequestMapper branchRequestMapper;

    @InjectMocks
    private BranchHandler branchHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBranch_ShouldReturnCreated() {
        BranchRequestDto branchRequestDto = new BranchRequestDto();
        branchRequestDto.setName("New Branch");
        Branch branch = new Branch();
        branch.setName("New Branch");

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create("/branch"))
                .body(Mono.just(branchRequestDto));

        when(branchRequestMapper.toDomain(branchRequestDto)).thenReturn(branch);
        when(branchServicePort.saveBranch(branch)).thenReturn(Mono.empty());

        StepVerifier.create(branchHandler.createBranch(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
                .verifyComplete();

        verify(branchRequestMapper, times(1)).toDomain(branchRequestDto);
        verify(branchServicePort, times(1)).saveBranch(branch);
    }


    @Test
    void testUpdateBranchName_ShouldReturnOk() {
        Long branchId = 1L;
        String newName = "Updated Branch";

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create("/branch/update"))
                .queryParam("branchId", branchId.toString())
                .queryParam("newName", newName)
                .build();

        when(branchServicePort.updateBranchName(branchId, newName)).thenReturn(Mono.empty());

        StepVerifier.create(branchHandler.updateBranchName(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(branchServicePort, times(1)).updateBranchName(branchId, newName);
    }
}
