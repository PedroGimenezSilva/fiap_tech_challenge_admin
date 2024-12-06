package br.com.challenge.admin.admin.repository;

import br.com.challenge.admin.admin.domain.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProdutoRepositoryTest {

    @InjectMocks
    private ProdutoRepository produtoRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        String expectedSql = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";

        // Act
        produtoRepository.save(produto);

        // Assert
        verify(jdbcTemplate, times(1)).update(expectedSql, produto.getNome(), produto.getPreco());
    }

    @Test
    void testDelete() {
        // Arrange
        int id = 1;
        String expectedSql = "DELETE FROM produtos WHERE id = ?";

        // Act
        produtoRepository.delete(id);

        // Assert
        verify(jdbcTemplate, times(1)).update(expectedSql, id);
    }
}
