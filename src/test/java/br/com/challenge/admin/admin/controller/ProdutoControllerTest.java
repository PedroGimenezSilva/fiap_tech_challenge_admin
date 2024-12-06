package br.com.challenge.admin.admin.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.challenge.admin.admin.domain.Produto;
import br.com.challenge.admin.admin.repository.ProdutoRepository;

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

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Produto Teste\",\"preco\":99.99}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto adicionado com sucesso!"));
    }
}