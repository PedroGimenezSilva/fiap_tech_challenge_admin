package br.com.challenge.admin.admin.domain;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

import br.com.challenge.admin.admin.exceptions.InvalidClienteException;

public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    /*
     * Construtor para criar um novo cliente
     */
    public Cliente(int id,String nome, String cpf, String email, String telefone) {
        validate(nome, cpf, email, telefone);
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Cliente(int id, String nome, String cpf, String email, String telefone, LocalDate createdAt, LocalDate updatedAt) {
        validate(nome, cpf, email, telefone);
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Cliente() {
    }

    private void validate(String nome, String cpf, String email, String telefone) {
        if (nome == null || nome.isBlank()) throw new InvalidClienteException("nome de cliente invalido");
        if (cpf == null || cpf.isBlank()) throw new InvalidClienteException("cpf de cliente invalido");
        if (email == null || email.isBlank()) throw new InvalidClienteException("email de cliente invalido");
        if (telefone == null || telefone.isBlank()) throw new InvalidClienteException("telefone de cliente invalido");
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }   

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return getId() == cliente.getId() && getNome().equals(cliente.getNome()) && getCpf().equals(cliente.getCpf()) && getEmail().equals(cliente.getEmail()) && getTelefone().equals(cliente.getTelefone()) && getCreatedAt().equals(cliente.getCreatedAt()) && getUpdatedAt().equals(cliente.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getCpf(), getEmail(), getTelefone(), getCreatedAt(), getUpdatedAt());
    }
}
