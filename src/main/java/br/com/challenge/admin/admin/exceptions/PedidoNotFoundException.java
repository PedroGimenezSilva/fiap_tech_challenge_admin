package br.com.challenge.admin.admin.exceptions;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException() {
        super("Pedido não existe");
    }
}
