package br.com.cdb.bancodigital.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/credito")
    public Cartao criarCartaoCredito(@RequestParam Long idConta, @RequestParam BigDecimal limite, @RequestParam String senha) {
        return cartaoService.criarCartaoCredito(idConta, limite, senha);
    }

    @PostMapping("/debito")
    public Cartao criarCartaoDebito(@RequestParam Long idConta, @RequestParam BigDecimal limite, @RequestParam String senha) {
        return cartaoService.criarCartaoDebito(idConta, limite, senha);
    }

    @PutMapping("/{id}/senha")
    public void alterarSenha(@PathVariable Long id, @RequestParam String senha) {
        cartaoService.alterarSenha(id, senha);
    }

    @PutMapping("/{id}/status")
    public void ativarOuDesativar(@PathVariable Long id, @RequestParam boolean ativar) {
        cartaoService.ativarDesativarCartao(id, ativar);
    }
}
