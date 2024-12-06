package br.com.challenge.admin.admin.repository;

import br.com.challenge.admin.admin.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClienteRepositoryTest {

    @InjectMocks
    private ClienteRepository clienteRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        Cliente cliente = new Cliente(1, "John Doe", "john.doe@example.com");
        String expectedSql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";

        // Act
        clienteRepository.save(cliente);

        // Assert
        verify(jdbcTemplate, times(1)).update(expectedSql, cliente.getNome(), cliente.getEmail());
    }

    @Test
    void testFindAll() {
        // Arrange
        String expectedSql = "SELECT * FROM clientes";
        Cliente cliente1 = new Cliente(1, "John Doe", "john.doe@example.com");
        Cliente cliente2 = new Cliente(2, "Jane Doe", "jane.doe@example.com");
        List<Cliente> expectedClientes = Arrays.asList(cliente1, cliente2);

        // Use a specific RowMapper mock to ensure compatibility
        when(jdbcTemplate.query(eq(expectedSql), any(RowMapper.class))).thenReturn(expectedClientes);

        // Act
        List<Cliente> actualClientes = clienteRepository.findAll();

        // Assert
        assertEquals(expectedClientes, actualClientes);
        verify(jdbcTemplate, times(1)).query(eq(expectedSql), any(RowMapper.class));
    }


    @Test
    void testUpdate() {
        // Arrange
        Cliente cliente = new Cliente(1, "John Doe Updated", "john.doe.updated@example.com");
        String expectedSql = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";

        // Act
        clienteRepository.update(cliente);

        // Assert
        verify(jdbcTemplate, times(1)).update(expectedSql, cliente.getNome(), cliente.getEmail(), cliente.getId());
    }

    @Test
    void testDelete() {
        // Arrange
        int id = 1;
        String expectedSql = "DELETE FROM clientes WHERE id = ?";

        // Act
        clienteRepository.delete(id);

        // Assert
        verify(jdbcTemplate, times(1)).update(expectedSql, id);
    }

    @Test
    void testFindById() {
        // Arrange
        int id = 1;
        String expectedSql = "SELECT * FROM clientes WHERE id = ?";
        Cliente expectedCliente = new Cliente(1, "John Doe", "john.doe@example.com");

        when(jdbcTemplate.queryForObject(eq(expectedSql), any(RowMapper.class), eq(id))).thenReturn(expectedCliente);

        // Act
        Cliente actualCliente = clienteRepository.findById(id);

        // Assert
        assertEquals(expectedCliente, actualCliente);
        verify(jdbcTemplate, times(1)).queryForObject(eq(expectedSql), any(RowMapper.class), eq(id));
    }
}
