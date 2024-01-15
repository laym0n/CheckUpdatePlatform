package com.victor.kochnev.rest.presenters.controller.advice;

import com.victor.kochnev.core.exception.UserRegistrationException;
import com.victor.kochnev.rest.presenters.api.dto.ErrorMessageDto;
import com.victor.kochnev.rest.presenters.controller.ControllerScanMarker;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice(basePackageClasses = ControllerScanMarker.class)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<Object> handleUserRegistrationException(UserRegistrationException ex, WebRequest request) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(409)).build();
    }

    @ExceptionHandler({AuthenticationException.class, JwtException.class})
    public ResponseEntity<Object> handleUserAuthorizationException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(new ErrorMessageDto().message("Проваленная аутентификация"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleTopLevelException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        return super.handleErrorResponseException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage());
        return super.handleMissingServletRequestPart(ex, headers, status, request);
    }


}
