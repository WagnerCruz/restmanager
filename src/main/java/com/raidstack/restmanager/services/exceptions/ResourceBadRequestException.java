package com.raidstack.restmanager.services.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceBadRequestException extends ResourceExceptionDefault {

    public ResourceBadRequestException(String message) {
        super(message);
        this.titulo = "Validação de Dados Incorreta";
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
