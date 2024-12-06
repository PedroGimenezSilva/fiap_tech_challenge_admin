package br.com.challenge.admin.admin.repository;

import br.com.challenge.admin.admin.domain.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";
        jdbcTemplate.update(sql, produto.getNome(), produto.getPreco());
    }

    public void delete(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Produto> findAll() {
        String sql = "SELECT * FROM produtos";
        RowMapper<Produto> rowMapper = (rs, rowNum) -> new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"));
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Produto findById(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        RowMapper<Produto> rowMapper = (rs, rowNum) -> new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
