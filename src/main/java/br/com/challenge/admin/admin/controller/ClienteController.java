package br.com.challenge.admin.admin.controller;

import br.com.challenge.admin.admin.domain.Cliente;
import br.com.challenge.admin.admin.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public String addCliente(@RequestBody Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente adicionado com sucesso!";
    }

    /*
     * Retorna todos os clientes
     */
    @GetMapping
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    /*
     * Retorna um cliente pelo id
     */
    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable int id) {
        return clienteRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        clienteRepository.delete(Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
