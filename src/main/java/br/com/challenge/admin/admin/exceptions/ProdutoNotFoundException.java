package br.com.challenge.admin.admin.exceptions;

public class ProdutoNotFoundException extends RuntimeException {
    public ProdutoNotFoundException(String id) {
        super(String.format("produto with id %s not found", id));
    }
}