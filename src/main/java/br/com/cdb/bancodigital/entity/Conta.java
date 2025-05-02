package br.com.cdb.bancodigital.entity;

import java.math.BigDecimal;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Conta {

    public abstract String getDescricaoTipoConta();

    public void transferir(Conta destino, BigDecimal valor) {
        this.sacar(valor);
        destino.depositar(valor);
    }

    public abstract BigDecimal getSaldo();

    public void pix(Conta destino, BigDecimal valor) {
        transferir(destino, valor);
    }

    public abstract void depositar(BigDecimal valor);

    public abstract void sacar(BigDecimal valor);
}
