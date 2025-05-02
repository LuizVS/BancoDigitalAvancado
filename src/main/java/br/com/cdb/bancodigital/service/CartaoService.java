package br.com.cdb.bancodigital.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;
import br.com.cdb.bancodigital.repository.CartaoRepository;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;
    
    @Autowired
    private ContaService contaService;    

    public Cartao criarCartaoCredito(Long idConta, BigDecimal limite, String senha) {
        CartaoCredito cartao = new CartaoCredito();
        cartao.setIdConta(idConta);
        cartao.setTipoCartao("CREDITO");
        cartao.setSenha(senha);
        cartao.ajustarLimite(limite);
        cartao.ativar();
        return cartaoRepository.save(cartao);
    }

    public Cartao criarCartaoDebito(Long idConta, BigDecimal limiteDiario, String senha) {
        CartaoDebito cartao = new CartaoDebito();
        cartao.setIdConta(idConta);
        cartao.setTipoCartao("DEBITO");
        cartao.setSenha(senha);
        cartao.ajustarLimite(limiteDiario);
        cartao.ativar();
        return cartaoRepository.save(cartao);
    }       

    public void alterarSenha(Long id, String novaSenha) {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        cartao.ifPresent(c -> {
            c.alterarSenha(novaSenha);
            cartaoRepository.save(c);
        });
    }

    public void ativarDesativarCartao(Long id, boolean ativar) {
        Optional<Cartao> cartao = cartaoRepository.findById(id);
        cartao.ifPresent(c -> {
            if (ativar) c.ativar();
            else c.desativar();
            cartaoRepository.save(c);
        });
    }
    
    public CartaoCredito emitirCartaoCredito(Long idConta, String tipoCliente, String senha) {
        BigDecimal limite;
        switch (tipoCliente.toUpperCase()) {
            case "PREMIUM":
                limite = BigDecimal.valueOf(10000);
                break;
            case "SUPER":
                limite = BigDecimal.valueOf(5000);
                break;
            default:
                limite = BigDecimal.valueOf(1000);
        }

        return (CartaoCredito) criarCartaoCredito(idConta, limite, senha);
    }

    public BigDecimal calcularTaxaDeUtilizacao(CartaoCredito cartao, BigDecimal totalGastoNoMes) {
        BigDecimal limite = cartao.getLimite();
        BigDecimal oitentaPorCentoDoLimite = limite.multiply(BigDecimal.valueOf(0.8));

        if (totalGastoNoMes.compareTo(oitentaPorCentoDoLimite) > 0) {
            return totalGastoNoMes.multiply(BigDecimal.valueOf(0.05)); // 5% de taxa
        }

        return BigDecimal.ZERO;
    }    
    
    public void aplicarTaxaManutencao(Long idConta, String tipoCliente) {
        Conta conta = contaService.buscarContaPorId(idConta); 
        if (conta instanceof ContaCorrente) {
            ContaCorrente contaCorrente = (ContaCorrente) conta;
            contaCorrente.calcularTaxaManutencao(tipoCliente);
            contaCorrente.aplicarTaxaManutencao();
            contaService.criarConta(contaCorrente); 
        }
    }

    public void aplicarRendimento(Long idConta) {
        Conta conta = contaService.buscarContaPorId(idConta);
        if (conta instanceof ContaPoupanca) {
            ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
            contaPoupanca.calcularTaxaRendimento(conta.getDescricaoTipoConta());
            contaPoupanca.aplicarRendimento();
            contaService.criarConta(contaPoupanca); 
        }
    }    
}
