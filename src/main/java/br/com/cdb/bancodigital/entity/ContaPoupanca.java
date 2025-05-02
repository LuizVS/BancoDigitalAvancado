package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conta_poupanca")
public class ContaPoupanca extends Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal saldo = BigDecimal.ZERO;

    private Long idCliente;

    private String tipoConta = "Poupança";
    
    private BigDecimal taxaRendimentoAnual;

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
    
    public void calcularTaxaRendimento(String tipoCliente) {
        if ("COMUM".equalsIgnoreCase(tipoCliente)) {
            taxaRendimentoAnual = BigDecimal.valueOf(0.005); // 0,5% ao ano
        } else if ("SUPER".equalsIgnoreCase(tipoCliente)) {
            taxaRendimentoAnual = BigDecimal.valueOf(0.007); // 0,7% ao ano
        } else if ("PREMIUM".equalsIgnoreCase(tipoCliente)) {
            taxaRendimentoAnual = BigDecimal.valueOf(0.009); // 0,9% ao ano
        }
    }    

    // Getters e Setters
    
    public void aplicarRendimento() {
        if (taxaRendimentoAnual != null) {
            BigDecimal rendimentoMensal = saldo.multiply(taxaRendimentoAnual)
                                                .divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
            saldo = saldo.add(rendimentoMensal);
        }
    }

    public BigDecimal getTaxaRendimentoAnual() {
        return taxaRendimentoAnual;
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