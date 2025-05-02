package br.com.cdb.bancodigital.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import br.com.cdb.bancodigital.entity.ContaCorrente;
import br.com.cdb.bancodigital.entity.ContaPoupanca;

@Repository
public class ContaDAO {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;	
    
    // === Conta Corrente ===
    public ContaCorrente salvarContaCorrente(ContaCorrente conta) {
        String sql = "INSERT INTO conta_corrente (id_cliente, saldo) VALUES (?, ?) RETURNING id";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        //Long id = jdbcTemplate.queryForObject(sql, Long.class, conta.getIdCliente(), conta.getSaldo());
        //conta.setId(id);
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, conta.getIdCliente());
            ps.setBigDecimal(2, conta.getSaldo());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            conta.setId(((Number) keys.get("id")).longValue());
        } else {
            throw new IllegalStateException("Erro ao obter o ID da conta corrente inserida.");
        }
               
        return conta;
    }
    
    public Optional<ContaCorrente> buscarContaCorrentePorId(Long id) {
        String sql = "SELECT * FROM conta_corrente WHERE id = ?";
        try {
            ContaCorrente conta = jdbcTemplate.queryForObject(sql, mapContaCorrente(), id);
            return Optional.of(conta);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public void atualizarSaldoContaCorrente(Long id, BigDecimal novoSaldo) {
        String sql = "UPDATE conta_corrente SET saldo = ? WHERE id = ?";
        jdbcTemplate.update(sql, novoSaldo, id);
    }    
    
    // === RowMappers Conta Corrente ===
    private RowMapper<ContaCorrente> mapContaCorrente() {
        return (ResultSet rs, int rowNum) -> {
            ContaCorrente conta = new ContaCorrente();
            conta.setId(rs.getLong("id"));
            conta.setIdCliente(rs.getLong("id_cliente"));
            conta.depositar(rs.getBigDecimal("saldo"));
            return conta;
        };
    }    
    
    
    // === Conta Poupança ===
    public ContaPoupanca salvarContaPoupanca(ContaPoupanca conta) {
        String sql = "INSERT INTO conta_poupanca (id_cliente, saldo) VALUES (?, ?) RETURNING id";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        //Long id = jdbcTemplate.queryForObject(sql, Long.class, conta.getIdCliente(), conta.getSaldo());
        //conta.setId(id);
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, conta.getIdCliente());
            ps.setBigDecimal(2, conta.getSaldo());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.containsKey("id")) {
            conta.setId(((Number) keys.get("id")).longValue());
        } else {
            throw new IllegalStateException("Erro ao obter o ID da conta poupança inserida.");
        }
        
        return conta;
    }

    public Optional<ContaPoupanca> buscarContaPoupancaPorId(Long id) {
        String sql = "SELECT * FROM conta_poupanca WHERE id = ?";
        try {
            ContaPoupanca conta = jdbcTemplate.queryForObject(sql, mapContaPoupanca(), id);
            return Optional.of(conta);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void atualizarSaldoContaPoupanca(Long id, BigDecimal novoSaldo) {
        String sql = "UPDATE conta_poupanca SET saldo = ? WHERE id = ?";
        jdbcTemplate.update(sql, novoSaldo, id);
    }
    
    // === RowMappers Conta Poupança ===    
    private RowMapper<ContaPoupanca> mapContaPoupanca() {
        return (ResultSet rs, int rowNum) -> {
            ContaPoupanca conta = new ContaPoupanca();
            conta.setId(rs.getLong("id"));
            conta.setIdCliente(rs.getLong("id_cliente"));
            conta.depositar(rs.getBigDecimal("saldo"));
            return conta;
        };
    }    

}
