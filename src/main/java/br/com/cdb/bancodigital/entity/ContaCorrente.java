package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta_corrente")
public class ContaCorrente extends Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal saldo = BigDecimal.ZERO;

    private Long idCliente;

    private String tipoConta = "Corrente";
    
    private BigDecimal taxaManutencao;

    @Override
    public String getDescricaoTipoConta() {
        return tipoConta;
    }

    @Override
    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public void depositar(BigDecimal valor) {
        saldo = saldo.add(valor);
    }

    @Override
    public void sacar(BigDecimal valor) {
        if (saldo.compareTo(valor) >= 0) {
            saldo = saldo.subtract(valor);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }
    
    public void calcularTaxaManutencao(String tipoCliente) {
        if ("COMUM".equalsIgnoreCase(tipoCliente)) {
            taxaManutencao = BigDecimal.valueOf(12.00);
        } else if ("SUPER".equalsIgnoreCase(tipoCliente)) {
            taxaManutencao = BigDecimal.valueOf(8.00);
        } else if ("PREMIUM".equalsIgnoreCase(tipoCliente)) {
            taxaManutencao = BigDecimal.ZERO; // isenta
        }
    }    

    // Getters e Setters
    public BigDecimal getTaxaManutencao() {
        return taxaManutencao;
    }

    public void aplicarTaxaManutencao() {
        // Subtrair a taxa de manutenção do saldo
        if (taxaManutencao != null) {
            saldo = saldo.subtract(taxaManutencao);
        }
    }    
    
    public Long getId() {
        return id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoConta() {
        return tipoConta;
    }
}
