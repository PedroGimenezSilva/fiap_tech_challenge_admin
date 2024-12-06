package br.com.challenge.admin.admin.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import br.com.challenge.admin.admin.exceptions.InvalidProdutoException;

public class Produto {
    private int id;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String imagem;
    private Categoria categoria;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Produto() {
    }

    public Produto(
            int id,
            String nome,
            BigDecimal preco,
            String descricao,
            String imagem,
            Categoria categoria,
            LocalDate createdAt,
            LocalDate updatedAt
    ) {
        validate(nome, preco, descricao);

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
        this.categoria = categoria;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(String nome, BigDecimal preco, String descricao) {
        if (nome == null || nome.isBlank())
            throw new InvalidProdutoException("nome de produto precisa ser informado");

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidProdutoException("preco precisa ser maior que zero");

        if (descricao == null || descricao.isBlank())
            throw new InvalidProdutoException("nome de produto precisa ser informado");

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto that = (Produto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getPreco(), that.getPreco()) && Objects.equals(getDescricao(), that.getDescricao()) && Objects.equals(getImagem(), that.getImagem()) && getCategoria() == that.getCategoria() && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getUpdatedAt(), that.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getPreco(), getDescricao(), getImagem(), getCategoria(), getCreatedAt(), getUpdatedAt());
    }
}
