package br.com.challenge.admin.admin.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.challenge.admin.admin.domain.Cliente;
import br.com.challenge.admin.admin.repository.ClienteRepository;

@SpringBootTest
class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }


    @Test
    void addCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("John Doe");
        cliente.setEmail("john.doe@example.com");

        doNothing().when(clienteRepository).save(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente adicionado com sucesso!"));
    }

     @Test
    void getClientes() throws Exception {
        Cliente cliente1 = new Cliente(1, "John Doe", "john.doe@example.com");
        Cliente cliente2 = new Cliente(2, "Jane Doe", "jane.doe@example.com");
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'nome':'John Doe','email':'john.doe@example.com'},{'id':2,'nome':'Jane Doe','email':'jane.doe@example.com'}]"));
    }

    @Test
    void getCliente() throws Exception {
        Cliente cliente = new Cliente(1, "John Doe", "john.doe@example.com");

        when(clienteRepository.findById(1)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'nome':'John Doe','email':'john.doe@example.com'}"));
    }

    @Test
    void deleteCliente() throws Exception {
        doNothing().when(clienteRepository).delete(1);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }
}