package br.com.cdb.bancodigital.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cdb.bancodigital.dao.ContaDAO;
import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;

@Service
public class ContaService {
	
    @Autowired
    private ContaDAO contaDao;	
    
    // === Operações com Conta Corrente ===
    public ContaCorrente criarContaCorrente(Long idCliente, BigDecimal saldoInicial) {
        ContaCorrente conta = new ContaCorrente();
        conta.setIdCliente(idCliente);
        conta.depositar(saldoInicial);
        return contaDao.salvarContaCorrente(conta);
    }
    
    public Optional<ContaCorrente> buscarContaCorrentePorId(Long id) {
        return contaDao.buscarContaCorrentePorId(id);
    }

    public void atualizarSaldoContaCorrente(Long idConta, BigDecimal novoSaldo) {
        contaDao.atualizarSaldoContaCorrente(idConta, novoSaldo);
    }
    
    // === Operações com Conta Poupança ===
    public ContaPoupanca criarContaPoupanca(Long idCliente, BigDecimal saldoInicial) {
        ContaPoupanca conta = new ContaPoupanca();
        conta.setIdCliente(idCliente);
        conta.depositar(saldoInicial);
        return contaDao.salvarContaPoupanca(conta);
    }

    public Optional<ContaPoupanca> buscarContaPoupancaPorId(Long id) {
        return contaDao.buscarContaPoupancaPorId(id);
    }

    public void atualizarSaldoContaPoupanca(Long idConta, BigDecimal novoSaldo) {
        contaDao.atualizarSaldoContaPoupanca(idConta, novoSaldo);
    }    
    

    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        // Buscar conta origem
        Optional<? extends Conta> origemOpt = contaDao.buscarContaCorrentePorId(idOrigem);
        if (!origemOpt.isPresent()) {
            origemOpt = contaDao.buscarContaPoupancaPorId(idOrigem);
        }

        // Buscar conta destino
        Optional<? extends Conta> destinoOpt = contaDao.buscarContaCorrentePorId(idDestino);
        if (!destinoOpt.isPresent()) {
            destinoOpt = contaDao.buscarContaPoupancaPorId(idDestino);
        }

        if (origemOpt.isEmpty() || destinoOpt.isEmpty()) {
            throw new RuntimeException("Conta de origem ou destino não encontrada");
        }

        Conta origem = origemOpt.get();
        Conta destino = destinoOpt.get();

        origem.transferir(destino, valor);

        if (origem instanceof ContaCorrente) {
        	ContaCorrente cc = (ContaCorrente) origem;
            contaDao.atualizarSaldoContaCorrente(cc.getId(), cc.getSaldo());
        } else {
        	ContaPoupanca cp = (ContaPoupanca) origem;
            contaDao.atualizarSaldoContaPoupanca(cp.getId(), cp.getSaldo());
        }

        if (destino instanceof ContaCorrente) {
        	ContaCorrente cc = (ContaCorrente) origem;
            contaDao.atualizarSaldoContaCorrente(cc.getId(), cc.getSaldo());
        } else {
        	ContaPoupanca cp = (ContaPoupanca) origem;
            contaDao.atualizarSaldoContaPoupanca(cp.getId(), cp.getSaldo());
        }
    }

    public void depositar(Long idConta, BigDecimal valor) {
        Optional<? extends Conta> contaOpt = contaDao.buscarContaCorrentePorId(idConta);
        if (!contaOpt.isPresent()) {
            contaOpt = contaDao.buscarContaPoupancaPorId(idConta);
        }

        Conta conta = contaOpt.orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.depositar(valor);

        if (conta instanceof ContaCorrente) {
        	ContaCorrente cc = (ContaCorrente) conta;
            contaDao.atualizarSaldoContaCorrente(cc.getId(), cc.getSaldo());
        } else {
        	ContaPoupanca cp = (ContaPoupanca) conta;
            contaDao.atualizarSaldoContaPoupanca(cp.getId(), cp.getSaldo());
        }
    }

    public void sacar(Long idConta, BigDecimal valor) {
        Optional<? extends Conta> contaOpt = contaDao.buscarContaCorrentePorId(idConta);
        if (!contaOpt.isPresent()) {
            contaOpt = contaDao.buscarContaPoupancaPorId(idConta);
        }

        Conta conta = contaOpt.orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.sacar(valor);

        if (conta instanceof ContaCorrente) {
        	ContaCorrente cc = (ContaCorrente) conta;
            contaDao.atualizarSaldoContaCorrente(cc.getId(), cc.getSaldo());
        } else {
        	ContaPoupanca cp = (ContaPoupanca) conta;
            contaDao.atualizarSaldoContaPoupanca(cp.getId(), cp.getSaldo());
        }
    }
    
    public void pix(Long idOrigem, Long idDestino, BigDecimal valor) {
        transferir(idOrigem, idDestino, valor); 
    }    
}
