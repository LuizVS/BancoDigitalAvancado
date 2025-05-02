package br.com.cdb.bancodigital.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cdb.bancodigital.dao.CartaoDAO;
import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;

@Service
public class CartaoService {
    
	@Autowired
	private CartaoDAO cartaoDAO;
	
    @Autowired
    private ContaService contaService;    

    public Cartao criarCartaoCredito(Long idConta, BigDecimal limite, String senha) {
        CartaoCredito cartao = new CartaoCredito();
        cartao.setIdConta(idConta);
        cartao.setTipoCartao("CREDITO");
        cartao.setSenha(senha);
        cartao.ajustarLimite(limite);
        cartao.ativar();
        return cartaoDAO.salvarCartao(cartao);
    }

    public Cartao criarCartaoDebito(Long idConta, BigDecimal limiteDiario, String senha) {
        CartaoDebito cartao = new CartaoDebito();
        cartao.setIdConta(idConta);
        cartao.setTipoCartao("DEBITO");
        cartao.setSenha(senha);
        cartao.ajustarLimite(limiteDiario);
        cartao.ativar();
        return cartaoDAO.salvarCartao(cartao);
    }     

    public void alterarSenha(Long id, String novaSenha) {
        Optional<Cartao> cartao = cartaoDAO.buscarCartaoPorId(id);
        cartao.ifPresent(c -> {
            c.alterarSenha(novaSenha);
            cartaoDAO.atualizarSenha(id, novaSenha);
        });
    }
    
    public void ativarDesativarCartao(Long id, boolean ativar) {
        Optional<Cartao> cartao = cartaoDAO.buscarCartaoPorId(id);
        cartao.ifPresent(c -> {
            if (ativar) c.ativar();
            else c.desativar();
            cartaoDAO.ativarDesativarCartao(id, ativar);
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
            return totalGastoNoMes.multiply(BigDecimal.valueOf(0.05));
        }

        return BigDecimal.ZERO;
    }
    
    public void aplicarTaxaManutencao(Long idConta, String tipoCliente) {    	
        ContaCorrente contaCorrente = contaService.buscarContaCorrentePorId(idConta)
            .orElseThrow(() -> new RuntimeException("Conta corrente não encontrada"));
        
        contaCorrente.calcularTaxaManutencao(tipoCliente);
        contaCorrente.aplicarTaxaManutencao();
        contaService.criarContaCorrente(contaCorrente.getId(), contaCorrente.getSaldo()); 
    }

    public void aplicarRendimento(Long idConta) {
        ContaPoupanca contaPoupanca = contaService.buscarContaPoupancaPorId(idConta)
            .orElseThrow(() -> new RuntimeException("Conta poupança não encontrada"));

        contaPoupanca.calcularTaxaRendimento(contaPoupanca.getDescricaoTipoConta());
        contaPoupanca.aplicarRendimento();
        contaService.criarContaPoupanca(contaPoupanca.getId(), contaPoupanca.getSaldo()); 
    }  
    
    public Optional<Cartao> buscarCartaoPorId(Long id) {  	
        return cartaoDAO.buscarCartaoPorId(id);                
    }    
    
    public Optional<Cartao> buscarCartaoPorNumero(String numero) {  	
        return cartaoDAO.buscarCartaoPorNumero(numero);                
    }        
}
