package br.com.cdb.bancodigital.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.cdb.bancodigital.entity.Cartao;
import br.com.cdb.bancodigital.entity.CartaoCredito;
import br.com.cdb.bancodigital.entity.CartaoDebito;

@Repository
public class CartaoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;	
	
    public Cartao salvarCartao(Cartao cartao) {
        String sql = "INSERT INTO cartao (idconta, numerocartao, tipocartao, senha, ativo, limite) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cartao.getIdConta());
            ps.setString(2, cartao.getNumeroCartao());
            ps.setString(3, cartao.getTipoCartao());
            ps.setString(4, cartao.getSenha());
            ps.setBoolean(5, cartao.isAtivo());
            ps.setBigDecimal(6, cartao.getLimite());
            return ps;
        }, keyHolder);

        cartao.setId(keyHolder.getKey().longValue());
        return cartao;
    }
    
    private RowMapper<Cartao> cartaoRowMapper() {
        return new RowMapper<Cartao>() {
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
        };
    } 
    
    public Optional<Cartao> buscarCartaoPorId(Long id) {
        String sql = "SELECT * FROM cartao WHERE id = ?";
        try {
            Cartao cartao = jdbcTemplate.queryForObject(sql, cartaoRowMapper(), id);
            return Optional.of(cartao);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public Optional<Cartao> buscarCartaoPorNumero(String numero) {
        String sql = "SELECT * FROM cartao WHERE numerocartao = ?";
        try {
            Cartao cartao = jdbcTemplate.queryForObject(sql, cartaoRowMapper(), numero);
            return Optional.of(cartao);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }    
    
    public void atualizarLimite(Long id, BigDecimal novoLimite) {
        String sql = "UPDATE cartao SET limite = ? WHERE id = ?";
        jdbcTemplate.update(sql, novoLimite, id);
    }

    public void atualizarSenha(Long id, String novaSenha) {
        String sql = "UPDATE cartao SET senha = ? WHERE id = ?";
        jdbcTemplate.update(sql, novaSenha, id);
    }

    public void ativarDesativarCartao(Long id, boolean ativo) {
        String sql = "UPDATE cartao SET ativo = ? WHERE id = ?";
        jdbcTemplate.update(sql, ativo, id);
    }    
      
    
}
