package br.com.challenge.admin.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.challenge.admin.admin.domain.Produto;
import br.com.challenge.admin.admin.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public String addProduto(@RequestBody Produto produto) {
        produtoRepository.save(produto);
        return "Produto adicionado com sucesso!";
    }

    



}
