package br.com.cdb.bancodigital.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql = "INSERT INTO cliente (idconta, tipocartao, senha, ativo) VALUES (?, ?, ?, ?)";        
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, cartao.getIdConta());
            ps.setString(2, cartao.getTipoCartao());
//            ps.setString(3, java.sql.Date.valueOf(cliente.getNascimento()));
            ps.setString(3, cartao.getSenha());
            ps.setBoolean(4, cartao.isAtivo());
            return ps;
        }, keyHolder); 
        

        cartao.setId(keyHolder.getKey().longValue());
        return cartao;        
    }      
    
    
}
