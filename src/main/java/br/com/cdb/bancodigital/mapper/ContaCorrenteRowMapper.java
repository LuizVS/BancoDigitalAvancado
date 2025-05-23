package br.com.cdb.bancodigital.mapper;

import br.com.cdb.bancodigital.entity.ContaCorrente;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaCorrenteRowMapper implements RowMapper<ContaCorrente> {
    @Override
    public ContaCorrente mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContaCorrente conta = new ContaCorrente();
        conta.setId(rs.getLong("id"));
        conta.setIdCliente(rs.getLong("id_cliente"));
        conta.depositar(rs.getBigDecimal("saldo"));
        return conta;
    }
}
