package br.com.challenge.admin.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.admin.admin.domain.Cliente;
import br.com.challenge.admin.admin.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
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
