package com.raidstack.restmanager.services.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceExceptionDefault extends RuntimeException {

    protected String titulo;
    protected HttpStatus httpStatus;

    public ResourceExceptionDefault(String message) {
        super(message);
        this.titulo = "Erro interno do sistema";
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getTitulo() {
        return titulo;
    }
}
