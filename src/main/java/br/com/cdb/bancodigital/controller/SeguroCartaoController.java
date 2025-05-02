package br.com.cdb.bancodigital.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cdb.bancodigital.entity.SeguroCartao;
import br.com.cdb.bancodigital.service.SeguroCartaoService;

@RestController
@RequestMapping("/seguros")
public class SeguroCartaoController {

    private final SeguroCartaoService seguroCartaoService;

    public SeguroCartaoController(SeguroCartaoService seguroCartaoService) {
        this.seguroCartaoService = seguroCartaoService;
    }

    @PostMapping("/viagem")
    public SeguroCartao criarSeguroViagem(@RequestParam Long idCliente, @RequestParam String tipoCliente) {
        return seguroCartaoService.criarSeguroViagem(idCliente, tipoCliente);
    }

    @PostMapping("/fraude")
    public SeguroCartao criarSeguroFraude(@RequestParam Long idCliente) {
        return seguroCartaoService.criarSeguroFraude(idCliente);
    }
}
