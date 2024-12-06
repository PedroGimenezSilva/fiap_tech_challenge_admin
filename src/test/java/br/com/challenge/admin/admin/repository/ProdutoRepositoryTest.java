package br.com.challenge.admin.admin.repository;

import br.com.challenge.admin.admin.domain.Categoria;
import br.com.challenge.admin.admin.domain.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProdutoRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        Produto produto = new Produto(1, "Produto Teste", new BigDecimal("99.99"), "Descrição do Produto Teste", "imagem", Categoria.BEBIDA, LocalDate.now(), LocalDate.now());
        String expectedSql = "INSERT INTO produtos (id, nome, preco, descricao , imagem, categoria, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Act
        produtoRepository.save(produto);

        // Assert
        verify(jdbcTemplate, times(1)).update(expectedSql, produto.getId(), produto.getNome(), produto.getPreco(), produto.getDescricao(), produto.getImagem(), produto.getCategoria(), produto.getCreatedAt(), produto.getUpdatedAt());
    }

    @Test
    void testDelete() {
        int id = 1;

        produtoRepository.delete(id);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object[]> paramsCaptor = ArgumentCaptor.forClass(Object[].class);

        verify(jdbcTemplate, times(1)).update(sqlCaptor.capture(), paramsCaptor.capture());
        assertEquals("DELETE FROM produtos WHERE id = ?", sqlCaptor.getValue());
        assertArrayEquals(new Object[]{1}, paramsCaptor.getValue());
    }

    @Test
    void testFindAll() {
        List<Produto> mockProdutos = Arrays.asList(
                new Produto(1, "Produto 1", new BigDecimal("10.0"), "Descrição do Produto Teste", "imagem", Categoria.BEBIDA, LocalDate.now(), LocalDate.now()),
                new Produto(2, "Produto 2", new BigDecimal("20.0"), "Descrição do Produto Teste", "imagem", Categoria.BEBIDA, LocalDate.now(), LocalDate.now())
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockProdutos);

        List<Produto> produtos = produtoRepository.findAll();

        assertEquals(2, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        assertEquals(BigDecimal.valueOf(10.0), produtos.get(0).getPreco());
        assertEquals("Produto 2", produtos.get(1).getNome());
        assertEquals(BigDecimal.valueOf(20.0), produtos.get(1).getPreco());

        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testFindById() {
        Produto mockProduto = new Produto(1, "Produto 1", new BigDecimal("10.0"), "Descrição do Produto Teste", "imagem", Categoria.BEBIDA, LocalDate.now(), LocalDate.now());
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(mockProduto);

        Produto produto = produtoRepository.findById(1);

        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        assertEquals(BigDecimal.valueOf(10.0), produto.getPreco());

        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), eq(1));
    }

    @Test
    void testFindAll2() throws SQLException {
        // Arrange
        String expectedSql = "SELECT * FROM produtos";
        Produto produto1 = new Produto(1, "Produto Teste 1", new BigDecimal("99.99"), "Descrição do Produto Teste 1", "imagem1.jpg", Categoria.LANCHE, LocalDate.now(), LocalDate.now());
        Produto produto2 = new Produto(2, "Produto Teste 2", new BigDecimal("199.99"), "Descrição do Produto Teste 2", "imagem2.jpg", Categoria.LANCHE, LocalDate.now(), LocalDate.now());
        List<Produto> expectedProdutos = Arrays.asList(produto1, produto2);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(produto1.getId(), produto2.getId());
        when(resultSet.getString("nome")).thenReturn(produto1.getNome(), produto2.getNome());
        when(resultSet.getBigDecimal("preco")).thenReturn(produto1.getPreco(), produto2.getPreco());
        when(resultSet.getString("descricao")).thenReturn(produto1.getDescricao(), produto2.getDescricao());
        when(resultSet.getString("imagem")).thenReturn(produto1.getImagem(), produto2.getImagem());
        when(resultSet.getString("categoria")).thenReturn(produto1.getCategoria().name(), produto2.getCategoria().name());
        when(resultSet.getObject("created_at", LocalDate.class)).thenReturn(produto1.getCreatedAt(), produto2.getCreatedAt());
        when(resultSet.getObject("updated_at", LocalDate.class)).thenReturn(produto1.getUpdatedAt(), produto2.getUpdatedAt());

        RowMapper<Produto> rowMapper = (rs, rowNum) -> new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getBigDecimal("preco"),
                rs.getString("descricao"),
                rs.getString("imagem"),
                Categoria.valueOf(rs.getString("categoria")),
                rs.getObject("created_at", LocalDate.class),
                rs.getObject("updated_at", LocalDate.class)
        );

        when(jdbcTemplate.query(eq(expectedSql), any(RowMapper.class))).thenAnswer(invocation -> {
            RowMapper<Produto> mapper = invocation.getArgument(1);
            return Arrays.asList(mapper.mapRow(resultSet, 0), mapper.mapRow(resultSet, 1));
        });

        // Act
        List<Produto> actualProdutos = produtoRepository.findAll();

        // Assert
        assertEquals(expectedProdutos, actualProdutos);
        verify(jdbcTemplate, times(1)).query(eq(expectedSql), any(RowMapper.class));
    }
}
