package br.com.cdb.bancodigital.mapper;

import br.com.cdb.bancodigital.entity.ContaPoupanca;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaPoupancaRowMapper implements RowMapper<ContaPoupanca> {
    @Override
    public ContaPoupanca mapRow(ResultSet rs, int rowNum) throws SQLException {       
            ContaPoupanca conta = new ContaPoupanca();
            conta.setId(rs.getLong("id"));
            conta.setIdCliente(rs.getLong("id_cliente"));
            conta.depositar(rs.getBigDecimal("saldo"));
            return conta;
    }
}
