package com.conexia.starwars.controller;

import com.conexia.starwars.domain.dto.ErrorResponseDTO;
import com.conexia.starwars.domain.enumeration.ErrorTypeEnum;
import com.conexia.starwars.exception.SWAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Filtro que maneja las excepciones que se lanzan en los rest controller,
 * les asigna un http status y devuelve un mensaje, tipo de error y codigo de uso interno
 */
@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger logger = LogManager.getLogger(ControllerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SWAPIException.class)
    protected ErrorResponseDTO handleSWAPIException(SWAPIException e) {
        return new ErrorResponseDTO(3312, e.getErrorTypeEnum(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponseDTO handleGenericException(Exception e) {
        logger.error("Error en el servidor ", e);
        return new ErrorResponseDTO(500, ErrorTypeEnum.SERVER, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {ExpiredJwtException.class, AuthenticationException.class})
    protected ErrorResponseDTO handleUnauthorizedException(RuntimeException e) {
        return new ErrorResponseDTO(401, ErrorTypeEnum.AUTH, e.getMessage());
    }
}
