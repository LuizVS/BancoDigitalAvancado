package br.com.cdb.bancodigital.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import br.com.cdb.bancodigital.entity.SeguroCartao;
import br.com.cdb.bancodigital.mapper.SeguroCartaoRowMapper;

@Repository
public class SeguroCartaoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public SeguroCartao salvar(SeguroCartao seguro) {
       // String sql = "INSERT INTO seguro_cartao (id_cliente, tipo_seguro, valor_mensal, cobertura, ativo) " +
       //              "VALUES (?, ?, ?, ?, ?) RETURNING id";
        String sql = "select * from public.inserir_seguro_cartao_v1 (?, ?, ?, ?, ?)";    	

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, seguro.getIdCliente());
            ps.setString(2, seguro.getTipoSeguro());
            ps.setBigDecimal(3, seguro.getValorMensal());
            ps.setBigDecimal(4, seguro.getCobertura());
            ps.setBoolean(5, seguro.isAtivo());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            seguro.setId(((Number) keys.get("id")).longValue());
        } else {
            throw new IllegalStateException("Erro ao obter o ID do seguro inserido.");
        }

        return seguro;
    }
    
    public Optional<SeguroCartao> buscarPorId(Long id) {
        String sql = "SELECT * FROM seguro_cartao WHERE id = ?";
        try {
            SeguroCartao seguro = jdbcTemplate.queryForObject(sql,  new SeguroCartaoRowMapper(), id);
            return Optional.ofNullable(seguro);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
	
}
