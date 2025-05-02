package br.com.cdb.bancodigital.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {
	
    @Autowired
    private ContaService contaService;

    @PostMapping
    public Conta criarConta(@RequestBody Conta conta) {
        return contaService.criarConta(conta);
    }

    @GetMapping("/{id}")
    public Optional<Conta> buscarConta(@PathVariable Long id) {
        return Optional.ofNullable(contaService.buscarContaPorId(id));
    }

    @PostMapping("/{id}/depositar")
    public void depositar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaService.depositar(id, valor);
    }

    @PostMapping("/{id}/sacar")
    public void sacar(@PathVariable Long id, @RequestParam BigDecimal valor) {
        contaService.sacar(id, valor);
    }

    @PostMapping("/transferir")
    public void transferir(@RequestParam Long origem, @RequestParam Long destino, @RequestParam BigDecimal valor) {
        contaService.transferir(origem, destino, valor);
    }

    @PostMapping("/pix")
    public void pix(@RequestParam Long origem, @RequestParam Long destino, @RequestParam BigDecimal valor) {
        contaService.pix(origem, destino, valor);
    }
}
