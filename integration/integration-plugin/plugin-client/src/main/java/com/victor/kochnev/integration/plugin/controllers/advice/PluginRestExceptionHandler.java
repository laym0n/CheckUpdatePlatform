package com.victor.kochnev.integration.plugin.controllers.advice;

import com.victor.kochnev.integration.plugin.api.dto.ErrorMessageDto;
import com.victor.kochnev.integration.plugin.controllers.ControllerScanMarker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
public class PluginRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleUserAuthorizationException(Exception ex, WebRequest request) {
        log.error(ExceptionUtils.getMessage(ex), ex);
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(new ErrorMessageDto().message("Проваленная аутентификация"));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleUserAuthorizationException(AccessDeniedException ex, WebRequest request) {
        String msg = ExceptionUtils.getMessage(ex);
        log.error(msg, ex);
        return ResponseEntity.status(HttpStatusCode.valueOf(401))
                .body(new ErrorMessageDto().message(msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleTopLevelException(Exception ex, WebRequest request) {
        log.error(ExceptionUtils.getMessage(ex), ex);
        return ResponseEntity.internalServerError().build();
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ExceptionUtils.getMessage(ex), ex);
        return super.handleErrorResponseException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ExceptionUtils.getMessage(ex), ex);
        return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ExceptionUtils.getMessage(ex), ex);
        return super.handleMissingServletRequestPart(ex, headers, status, request);
    }


}
