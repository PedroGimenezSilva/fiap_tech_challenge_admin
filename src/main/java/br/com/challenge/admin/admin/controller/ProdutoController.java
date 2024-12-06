package br.com.challenge.admin.admin.controller;

import br.com.challenge.admin.admin.domain.Categoria;
import br.com.challenge.admin.admin.domain.Produto;
import br.com.challenge.admin.admin.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public String addProduto(@RequestBody Produto produto) {
        produtoRepository.save(produto);
        return "Produto adicionado com sucesso!";
    }

    /*
     * Retorna todos os produtos
     */
    @GetMapping
    public List<Produto> getProdutos() {
        return produtoRepository.findAll();
    }

    /*
     * Retorna um produto pelo id
     */
    @GetMapping("/{id}")
    public Produto getProduto(@PathVariable int id) {
        return produtoRepository.findById(id);
    }

    /*
     * Remove um produto pelo id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        produtoRepository.delete(Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
