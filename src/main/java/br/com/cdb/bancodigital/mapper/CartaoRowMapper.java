package br.com.cdb.bancodigital.mapper;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaoRowMapper implements RowMapper<Cartao> {
    @Override
    public Cartao mapRow(ResultSet rs, int rowNum) throws SQLException {
        String tipo = rs.getString("tipocartao");
        Cartao cartao;

        if ("CREDITO".equalsIgnoreCase(tipo)) {
            cartao = new CartaoCredito();
        } else {
            cartao = new CartaoDebito();
        }

        cartao.setId(rs.getLong("id"));
        cartao.setIdConta(rs.getLong("idconta"));
        cartao.setNumeroCartao(rs.getString("numerocartao"));                
        cartao.setTipoCartao(tipo);
        cartao.setSenha(rs.getString("senha"));
        cartao.setAtivo(rs.getBoolean("ativo"));
        cartao.ajustarLimite(rs.getBigDecimal("limite"));

        return cartao;
    }
}
