package com.pragma.franchise.infrastructure.entrypoints.exceptionhandler;

import com.pragma.franchise.domain.exception.DomainException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerAdvisorTest {

    private final ControllerAdvisor controllerAdvisor = new ControllerAdvisor();

    @Test
    void testHandleDomainException() {
        DomainException domainException = new DomainException("Custom domain error");

        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleDomainException(domainException);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Custom domain error", response.getBody().get("message"));
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Generic error");

        ResponseEntity<Map<String, String>> response = controllerAdvisor.handleException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Generic error", response.getBody().get("message"));
    }

    @Test
    void testHandleValidationExceptions() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("object", "field1", "Field1 is required"),
                new FieldError("object", "field2", "Field2 must be a number")
        ));

        WebExchangeBindException exception = new WebExchangeBindException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = controllerAdvisor.handleValidationExceptions(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation Error", response.getBody().get("error"));

        Map<String, String> errors = (Map<String, String>) response.getBody().get("errors");
        assertEquals(2, errors.size());
        assertEquals("Field1 is required", errors.get("field1"));
        assertEquals("Field2 must be a number", errors.get("field2"));
    }
}
