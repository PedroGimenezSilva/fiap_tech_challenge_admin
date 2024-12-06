package br.com.challenge.admin.admin.repository;

import br.com.challenge.admin.admin.domain.Categoria;
import br.com.challenge.admin.admin.domain.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProdutoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Produto produto) {
        String sql = "INSERT INTO produtos (id, nome, preco, descricao , imagem, categoria, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,produto.getId(), produto.getNome(), produto.getPreco(), produto.getDescricao(), produto.getImagem(), produto.getCategoria(), produto.getCreatedAt(),
                produto.getUpdatedAt());
    }

    public void delete(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Produto> findAll() {
        String sql = "SELECT * FROM produtos";
        RowMapper<Produto> rowMapper = (rs, rowNum) -> new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getBigDecimal("preco"),
                rs.getString("descricao"),
                rs.getString("imagem"),
                Categoria.valueOf(rs.getString("categoria")),
                rs.getObject("created_at", LocalDate.class),
                rs.getObject("updated_at", LocalDate.class));
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Produto findById(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        RowMapper<Produto> rowMapper = (rs, rowNum) -> new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getBigDecimal("preco"),
                rs.getString("descricao"),
                rs.getString("imagem"),
                Categoria.valueOf(rs.getString("categoria")),
                rs.getObject("created_at", LocalDate.class),
                rs.getObject("updated_at", LocalDate.class));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}