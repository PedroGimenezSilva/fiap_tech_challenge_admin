package br.com.challenge.admin.admin.repository;

import br.com.challenge.admin.admin.domain.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        Produto produto = new Produto(0, "Produto Teste", 30.0);

        produtoRepository.save(produto);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object[]> paramsCaptor = ArgumentCaptor.forClass(Object[].class);

        verify(jdbcTemplate, times(1)).update(sqlCaptor.capture(), paramsCaptor.capture());
        assertEquals("INSERT INTO produtos (nome, preco) VALUES (?, ?)", sqlCaptor.getValue());
        assertArrayEquals(new Object[]{"Produto Teste", 30.0}, paramsCaptor.getValue());
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
                new Produto(1, "Produto 1", 10.0),
                new Produto(2, "Produto 2", 20.0)
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockProdutos);

        List<Produto> produtos = produtoRepository.findAll();

        assertEquals(2, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        assertEquals(10.0, produtos.get(0).getPreco());
        assertEquals("Produto 2", produtos.get(1).getNome());
        assertEquals(20.0, produtos.get(1).getPreco());

        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testFindById() {
        Produto mockProduto = new Produto(1, "Produto 1", 10.0);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(mockProduto);

        Produto produto = produtoRepository.findById(1);

        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        assertEquals(10.0, produto.getPreco());

        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), eq(1));
    }
}
