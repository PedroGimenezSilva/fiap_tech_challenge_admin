package br.com.challenge.admin.admin.exceptions;

public class InvalidProdutoException extends RuntimeException {
    public InvalidProdutoException(String message) {
        super(message);
    }
}