package com.conexia.starwars.exception;

import com.conexia.starwars.domain.enumeration.ErrorTypeEnum;
import lombok.Getter;

@Getter
public class SWAPIException extends Throwable {

    private final ErrorTypeEnum errorTypeEnum;

    public SWAPIException(String message, Throwable cause) {
        super(message, cause);
        this.errorTypeEnum = ErrorTypeEnum.EXTERNAL_API;
    }

    public SWAPIException() {
        super("External API error");
        this.errorTypeEnum = ErrorTypeEnum.EXTERNAL_API;
    }
}
