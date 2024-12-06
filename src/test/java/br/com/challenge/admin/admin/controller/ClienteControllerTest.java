package br.com.challenge.admin.admin.controller;

import br.com.challenge.admin.admin.domain.Cliente;
import br.com.challenge.admin.admin.repository.ClienteRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    cliente.setCpf("12345678901");
    cliente.setEmail("john.doe@example.com");
    cliente.setTelefone("11999999999");

    doNothing().when(clienteRepository).save(any(Cliente.class));

        mockMvc.perform(post("/api/admin/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"John Doe\",\"cpf\":\"12345678901\",\"email\":\"john.doe@example.com\",\"telefone\":\"11999999999\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente adicionado com sucesso!"));
    }

    @Test
    void getClientes() throws Exception {
        Cliente cliente1 = new Cliente(1, "John Doe", "12345678901", "john.doe@example.com", "84988563214", null, null);
        Cliente cliente2 = new Cliente(2, "Jane Doe", "05425165895", "jane.doe@example.com", "84988563215", null, null);
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/api/admin/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'nome':'John Doe','email':'john.doe@example.com'},{'id':2,'nome':'Jane Doe','email':'jane.doe@example.com'}]"));
    }

    @Test
    void getCliente() throws Exception {
        Cliente cliente = new Cliente(1,"John Doe", "05425165895", "john.doe@example.com", "11999999999"); 
        //validate(nome, cpf, email, telefone);

        when(clienteRepository.findById(1)).thenReturn(cliente);

        mockMvc.perform(get("/api/admin/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'nome':'John Doe','email':'john.doe@example.com'}"));
    }

    @Test
    void deleteCliente() throws Exception {
        doNothing().when(clienteRepository).delete(1);

        mockMvc.perform(delete("/api/admin/clientes/1"))
                .andExpect(status().isNoContent());
    }
}