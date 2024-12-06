package br.com.challenge.admin.admin.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String id) {
        super(String.format("cliente with id %s not found", id));
    }
}