package br.com.challenge.admin.admin.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
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

    /*
     * teste de inserção de cliente
     */
    public void save(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, email, telefone, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getTelefone(), LocalDate.now(), LocalDate.now());  
    }

    /*
     * Retorna todos os clientes
     */
    public List<Cliente> findAll() {
        String sql = "SELECT * FROM clientes";
        RowMapper<Cliente> rowMapper = (rs, rowNum) -> new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("email"), rs.getString("telefone"), rs.getObject("created_at", LocalDate.class), rs.getObject("updated_at", LocalDate.class));
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
        RowMapper<Cliente> rowMapper = (rs, rowNum) -> new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("email"), rs.getString("telefone"), rs.getObject("created_at", LocalDate.class), rs.getObject("updated_at", LocalDate.class));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
