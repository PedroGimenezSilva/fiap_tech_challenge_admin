package br.com.challenge.admin.admin.controller;

import br.com.challenge.admin.admin.domain.Produto;
import br.com.challenge.admin.admin.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    void addProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto Teste");
        produto.setPreco(99.99);

        doNothing().when(produtoRepository).save(produto);

        mockMvc.perform(post("/api/admin/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Produto Teste\",\"preco\":99.99}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto adicionado com sucesso!"));
    }

    @Test
    void testGetProdutos() throws Exception {
        // Arrange
        Produto produto1 = new Produto(1, "Produto 1", 10.00);
        Produto produto2 = new Produto(2, "Produto 2", 20.00);
        List<Produto> produtos = Arrays.asList(produto1, produto2);
        when(produtoRepository.findAll()).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/admin/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"));

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void testGetProduto() throws Exception {
        // Arrange
        Produto produto = new Produto(1, "Produto Teste", 19.99);
        when(produtoRepository.findById(1)).thenReturn(produto);

        // Act & Assert
        mockMvc.perform(get("/api/admin/produtos/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"))
                .andExpect(jsonPath("$.preco").value(19.99));

        verify(produtoRepository, times(1)).findById(1);
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        int id = 1;

        // Act & Assert
        mockMvc.perform(delete("/api/admin/produtos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(produtoRepository, times(1)).delete(id);
    }
}