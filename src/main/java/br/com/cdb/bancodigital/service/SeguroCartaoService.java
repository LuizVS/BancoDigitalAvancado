package br.com.cdb.bancodigital.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cdb.bancodigital.dao.SeguroCartaoDAO;
import br.com.cdb.bancodigital.entity.SeguroCartao;

@Service
public class SeguroCartaoService {

	@Autowired
	private SeguroCartaoDAO segurocartaoDAO;

    public SeguroCartao criarSeguroViagem(Long idCliente, String tipoCliente) {
        SeguroCartao seguro = new SeguroCartao();
        seguro.setIdCliente(idCliente);
        seguro.setTipoSeguro("VIAGEM");
        seguro.setAtivo(true);

        if ("PREMIUM".equalsIgnoreCase(tipoCliente)) {
            seguro.setValorMensal(BigDecimal.ZERO);
        } else {
            seguro.setValorMensal(BigDecimal.valueOf(50));
        }

        seguro.setCobertura(BigDecimal.valueOf(10000));
        return segurocartaoDAO.salvar(seguro);
    }

    public SeguroCartao criarSeguroFraude(Long idCliente) {
        SeguroCartao seguro = new SeguroCartao();
        seguro.setIdCliente(idCliente);
        seguro.setTipoSeguro("FRAUDE");
        seguro.setValorMensal(BigDecimal.ZERO);
        seguro.setCobertura(BigDecimal.valueOf(5000));
        seguro.setAtivo(true);
        return segurocartaoDAO.salvar(seguro);
    }

    public Optional<SeguroCartao> buscarPorId(Long id) {
        return segurocartaoDAO.buscarPorId(id);
    }
}
