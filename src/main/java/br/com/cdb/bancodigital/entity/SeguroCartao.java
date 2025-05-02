package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;

public class SeguroCartao {

    private Long id;
	private Long idCliente;
    private String tipoSeguro; // "VIAGEM" ou "FRAUDE"
    private BigDecimal valorMensal;
    private BigDecimal cobertura;
    private boolean ativo;

    // Getters e Setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoSeguro() {
		return tipoSeguro;
	}

	public void setTipoSeguro(String tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
	}

	public BigDecimal getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(BigDecimal valorMensal) {
		this.valorMensal = valorMensal;
	}

	public BigDecimal getCobertura() {
		return cobertura;
	}

	public void setCobertura(BigDecimal cobertura) {
		this.cobertura = cobertura;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}    
}
