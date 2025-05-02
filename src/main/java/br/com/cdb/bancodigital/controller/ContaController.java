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

import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {
	
    @Autowired
    private ContaService contaService;

    // ----------- Conta Corrente -------------
    @PostMapping("/corrente")
    public ContaCorrente criarContaCorrente(@RequestBody ContaCorrente contaCorrente) {
        return contaService.criarContaCorrente(contaCorrente.getId(), contaCorrente.getSaldo());
    }

    @GetMapping("/corrente/{id}")
    public Optional<ContaCorrente> buscarContaCorrente(@PathVariable Long id) {
        return contaService.buscarContaCorrentePorId(id);
    }
    // ----------------------------------------
    
    // ----------- Conta Poupança -------------
    @PostMapping("/poupanca")
    public ContaPoupanca criarContaPoupanca(@RequestBody ContaPoupanca contaPoupanca) {
        return contaService.criarContaPoupanca(contaPoupanca.getId(), contaPoupanca.getSaldo());
    }

    @GetMapping("/poupanca/{id}")
    public Optional<ContaPoupanca> buscarContaPoupanca(@PathVariable Long id) {
        return contaService.buscarContaPoupancaPorId(id);
    }    
    // ----------------------------------------    
    
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
