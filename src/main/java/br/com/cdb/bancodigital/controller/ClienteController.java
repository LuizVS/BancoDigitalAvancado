package br.com.cdb.bancodigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cdb.bancodigital.entity.Cliente;
import br.com.cdb.bancodigital.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/novo")
    public ResponseEntity<String> criar(@RequestBody Cliente cliente) {
        try {
            clienteService.salvarCliente(
                cliente.getNome(), cliente.getCpf(), cliente.getNascimento(),
                cliente.getRua(), cliente.getNumero(), cliente.getComplemento(),
                cliente.getCidade(), cliente.getEstado(), cliente.getCep(), cliente.getTipo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente criado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar cliente: " + e.getMessage());
        }
    }
	
	@GetMapping("/listartodos")
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

	
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @Validated @RequestBody Cliente cliente) {
        try {
            Cliente atualizado = clienteService.atualizarCliente(id, cliente);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar cliente: " + e.getMessage());
        }
    } 
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            return ResponseEntity.ok("Cliente deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar cliente: " + e.getMessage());
        }
    }
	
}
