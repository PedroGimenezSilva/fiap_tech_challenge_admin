package br.com.challenge.admin.admin.exceptions;

public class InvalidClienteException extends RuntimeException {
    public InvalidClienteException(String message) {
        super(message);
    }
}