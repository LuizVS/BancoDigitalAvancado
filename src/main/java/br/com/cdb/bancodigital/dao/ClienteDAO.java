package br.com.cdb.bancodigital.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.cdb.bancodigital.entity.Cliente;

@Repository
public class ClienteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Cliente salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, nascimento, rua, numero, complemento, cidade, estado, cep, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNome());
            ps.setLong(2, cliente.getCpf());
            ps.setDate(3, java.sql.Date.valueOf(cliente.getNascimento()));
            ps.setString(4, cliente.getRua());
            ps.setString(5, cliente.getNumero());
            ps.setString(6, cliente.getComplemento());
            ps.setString(7, cliente.getCidade());
            ps.setString(8, cliente.getEstado());
            ps.setString(9, cliente.getCep());
            ps.setString(10, cliente.getTipo());
            return ps;
        }, keyHolder); 
        

        cliente.setId(keyHolder.getKey().longValue());
        return cliente;        
        
        //jdbcTemplate.update(sql, cliente.getNome(), cliente.getCpf(), java.sql.Date.valueOf(cliente.getNascimento()), cliente.getRua(), 
        //		                 cliente.getNumero(), cliente.getComplemento(), cliente.getCidade(), cliente.getEstado(),
        //		                 cliente.getCep(), cliente.getTipo());
    }   
    
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM cliente order by id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Cliente c = new Cliente();
            c.setId(rs.getLong("id"));
            c.setNome(rs.getString("nome"));
            c.setCpf(rs.getLong("cpf"));
            c.setNascimento(rs.getDate("nascimento").toLocalDate());
            c.setRua(rs.getString("rua"));
            c.setNumero(rs.getString("numero"));
            c.setComplemento(rs.getString("complemento"));
            c.setCidade(rs.getString("cidade"));
            c.setEstado(rs.getString("estado"));
            c.setCep(rs.getString("cep"));
            c.setTipo(rs.getString("tipo"));            
            return c;
        });
    }    
    
    public Optional<Cliente> buscarClientePorId(Long id) {
        String sql = "SELECT * FROM cliente where id = ?";
        try {
            Cliente cliente = jdbcTemplate.queryForObject(sql, clienteRowMapper(), id);
            return Optional.of(cliente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }       
    
    private RowMapper<Cliente> clienteRowMapper() {
    	return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getLong("cpf"));
                c.setNascimento(rs.getDate("nascimento").toLocalDate());
                c.setRua(rs.getString("rua"));
                c.setNumero(rs.getString("numero"));
                c.setComplemento(rs.getString("complemento"));
                c.setCidade(rs.getString("cidade"));
                c.setEstado(rs.getString("estado"));
                c.setCep(rs.getString("cep"));
                c.setTipo(rs.getString("tipo"));
                return c;
            }
        };
    }

	public Optional<Cliente> buscarClientePorCPF(Long cpf) {
        String sql = "SELECT * FROM cliente where cpf = ?";
        try {
            Cliente cliente = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getLong("cpf"));
                c.setNascimento(rs.getDate("nascimento").toLocalDate());
                c.setRua(rs.getString("rua"));
                c.setNumero(rs.getString("numero"));
                c.setComplemento(rs.getString("complemento"));
                c.setCidade(rs.getString("cidade"));
                c.setEstado(rs.getString("estado"));
                c.setCep(rs.getString("cep"));
                c.setTipo(rs.getString("tipo"));
                return c;
            }, cpf);
            
            return Optional.of(cliente);
            
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // Nenhum cliente encontrado com esse CPF
        }            
    }     
    
    public void atualizarCliente(Cliente cliente) {
    	String sql = "update cliente set nome = ?, cpf = ?, nascimento = ?, rua = ?, numero = ?, complemento = ?, cidade = ?, estado = ?, cep = ?, tipo = ? where id = ?";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getCpf(), java.sql.Date.valueOf(cliente.getNascimento()), cliente.getRua(), 
                cliente.getNumero(), cliente.getComplemento(), cliente.getCidade(), cliente.getEstado(),
                cliente.getCep(), cliente.getTipo(), cliente.getId());    	
    }
    
    public void apagarCliente(Long id) {
    	String sql = "delete from cliente where id = ?";
        jdbcTemplate.update(sql, id);    	
    }    
    
}
