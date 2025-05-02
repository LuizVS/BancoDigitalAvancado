package br.com.cdb.bancodigital.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cdb.bancodigital.entity.Conta;
import br.com.cdb.bancodigital.repository.ContaRepository;

@Service
public class ContaService {
	
    @Autowired
    private ContaRepository contaRepository;

    public Conta criarConta(Conta conta) {
        return contaRepository.save(conta);
    }

    public Conta buscarContaPorId(Long id) {
    	return contaRepository.findById(id).orElse(null);
    }

    public void transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        Conta origem = contaRepository.findById(idOrigem).orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));
        Conta destino = contaRepository.findById(idDestino).orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));
        origem.transferir(destino, valor);
        contaRepository.save(origem);
        contaRepository.save(destino);
    }

    public void pix(Long idOrigem, Long idDestino, BigDecimal valor) {
        transferir(idOrigem, idDestino, valor); 
    }

    public void depositar(Long idConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.depositar(valor);
        contaRepository.save(conta);
    }

    public void sacar(Long idConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.sacar(valor);
        contaRepository.save(conta);
    }
}
