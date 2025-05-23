package br.com.cdb.bancodigital.mapper;

import br.com.cdb.bancodigital.entity.SeguroCartao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeguroCartaoRowMapper implements RowMapper<SeguroCartao> {
    @Override
    public SeguroCartao mapRow(ResultSet rs, int rowNum) throws SQLException {
        SeguroCartao seguro = new SeguroCartao();
        seguro.setId(rs.getLong("id"));
        seguro.setIdCliente(rs.getLong("id_cliente"));
        seguro.setTipoSeguro(rs.getString("tipo_seguro"));
        seguro.setValorMensal(rs.getBigDecimal("valor_mensal"));
        seguro.setCobertura(rs.getBigDecimal("cobertura"));
        seguro.setAtivo(rs.getBoolean("ativo"));
        return seguro;
    }
}
