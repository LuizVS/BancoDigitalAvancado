package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;

public abstract class Cartao {

    private Long id;

    private Long idConta;
    private String numeroCartao;
	private String tipoCartao; // "CREDITO" ou "DEBITO"
    private String senha;
    private boolean ativo;
    private BigDecimal limite;

	public abstract void pagar(BigDecimal valor);
    public abstract void alterarSenha(String novaSenha);
    public abstract void ajustarLimite(BigDecimal novoLimite);

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
    
    // Getters e Setters    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	public String getTipoCartao() {
		return tipoCartao;
	}
	public void setTipoCartao(String tipoCartao) {
		this.tipoCartao = tipoCartao;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
    
    public BigDecimal getLimite() {
		return limite;
	}
	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}	
	
    public String getNumeroCartao() {
		return numeroCartao;
	}
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}	
	
}
