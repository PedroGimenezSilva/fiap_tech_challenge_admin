package br.com.challenge.admin.admin.exceptions;

public class InvalidPagamentoException extends RuntimeException {
    public InvalidPagamentoException(String message) {
        super(message);
    }
}