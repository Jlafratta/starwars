package com.conexia.starwars.domain.dto;

import com.conexia.starwars.domain.enumeration.ErrorTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {
    int code;   // codigo de uso interno para manejo de errores
    ErrorTypeEnum errorType;
    String message;
}
