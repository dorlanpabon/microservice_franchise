package com.pragma.franchise.infrastructure.entrypoints.handler;

import com.pragma.franchise.domain.model.Franchise;
import com.pragma.franchise.domain.spi.IFranchiseServicePort;
import com.pragma.franchise.infrastructure.entrypoints.dto.request.FranchiseRequestDto;
import com.pragma.franchise.infrastructure.entrypoints.mapper.IFranchiseRequestMapper;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

class FranchiseHandlerTest {

    @Mock
    private IFranchiseServicePort franchiseServicePort;

    @Mock
    private IFranchiseRequestMapper franchiseRequestMapper;

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFranchise_ShouldReturnCreated() {
        FranchiseRequestDto franchiseRequestDto = new FranchiseRequestDto();
        franchiseRequestDto.setName("New Franchise");
        Franchise franchise = new Franchise();
        franchise.setName("New Franchise");

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create("/franchise"))
                .body(Mono.just(franchiseRequestDto));

        when(franchiseRequestMapper.toDomain(franchiseRequestDto)).thenReturn(franchise);
        when(franchiseServicePort.save(franchise)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseHandler.createFranchise(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
                .verifyComplete();

        verify(franchiseRequestMapper, times(1)).toDomain(franchiseRequestDto);
        verify(franchiseServicePort, times(1)).save(franchise);
    }

    @Test
    void testUpdateFranchiseName_ShouldReturnOk() {
        Long franchiseId = 1L;
        String newName = "Updated Franchise";

        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.PUT)
                .uri(URI.create("/franchise"))
                .queryParam("franchiseId", franchiseId.toString())
                .queryParam("newName", newName)
                .build();

        when(franchiseServicePort.updateFranchiseName(franchiseId, newName)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseHandler.updateFranchiseName(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(franchiseServicePort, times(1)).updateFranchiseName(franchiseId, newName);
    }

}
