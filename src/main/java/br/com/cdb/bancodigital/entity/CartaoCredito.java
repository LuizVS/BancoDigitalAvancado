package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;

public class CartaoCredito extends Cartao {

    private BigDecimal limite;
    private BigDecimal saldoUtilizado = BigDecimal.ZERO;

    @Override
    public void pagar(BigDecimal valor) {
        if (saldoUtilizado.add(valor).compareTo(limite) <= 0) {
            saldoUtilizado = saldoUtilizado.add(valor);
        } else {
            throw new RuntimeException("Limite do cartão de crédito excedido.");
        }
    }

    @Override
    public void alterarSenha(String novaSenha) {
        setSenha(novaSenha);
    }

    @Override
    public void ajustarLimite(BigDecimal novoLimite) {
        this.limite = novoLimite;
    }

    // Getters e Setters    
	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public BigDecimal getSaldoUtilizado() {
		return saldoUtilizado;
	}

	public void setSaldoUtilizado(BigDecimal saldoUtilizado) {
		this.saldoUtilizado = saldoUtilizado;
	}

}
