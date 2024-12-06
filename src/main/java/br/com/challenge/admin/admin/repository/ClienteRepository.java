package br.com.challenge.admin.admin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.com.challenge.admin.admin.domain.Cliente;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail());
    }

    /*
     * Retorna todos os clientes
     */
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM clientes";
        RowMapper<Cliente> rowMapper = (rs, rowNum) -> new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Cliente findById(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        RowMapper<Cliente> rowMapper = (rs, rowNum) -> new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
