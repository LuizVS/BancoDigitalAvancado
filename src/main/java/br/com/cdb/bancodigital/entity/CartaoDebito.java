package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CartaoDebito extends Cartao {

    private BigDecimal limiteDiario;
    private BigDecimal totalUsadoHoje = BigDecimal.ZERO;
    private LocalDate dataUltimoUso = LocalDate.now();

    @Override
    public void pagar(BigDecimal valor) {
        if (!dataUltimoUso.equals(LocalDate.now())) {
            totalUsadoHoje = BigDecimal.ZERO;
            dataUltimoUso = LocalDate.now();
        }

        if (totalUsadoHoje.add(valor).compareTo(limiteDiario) <= 0) {
            totalUsadoHoje = totalUsadoHoje.add(valor);
        } else {
            throw new RuntimeException("Limite diário excedido.");
        }
    }

    @Override
    public void alterarSenha(String novaSenha) {
        setSenha(novaSenha);
    }

    @Override
    public void ajustarLimite(BigDecimal novoLimite) {
        this.limiteDiario = novoLimite;
    }

    // Getters e Setters    
	public BigDecimal getLimiteDiario() {
		return limiteDiario;
	}

	public void setLimiteDiario(BigDecimal limiteDiario) {
		this.limiteDiario = limiteDiario;
	}

	public BigDecimal getTotalUsadoHoje() {
		return totalUsadoHoje;
	}

	public void setTotalUsadoHoje(BigDecimal totalUsadoHoje) {
		this.totalUsadoHoje = totalUsadoHoje;
	}

	public LocalDate getDataUltimoUso() {
		return dataUltimoUso;
	}

	public void setDataUltimoUso(LocalDate dataUltimoUso) {
		this.dataUltimoUso = dataUltimoUso;
	}

}
