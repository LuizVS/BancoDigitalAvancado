package br.com.cdb.bancodigital.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.cdb.bancodigital.entity.SeguroCartao;
import br.com.cdb.bancodigital.repository.SeguroCartaoRepository;

@Service
public class SeguroCartaoService {

    private final SeguroCartaoRepository seguroCartaoRepository;

    public SeguroCartaoService(SeguroCartaoRepository seguroCartaoRepository) {
        this.seguroCartaoRepository = seguroCartaoRepository;
    }

    public SeguroCartao criarSeguroViagem(Long idCliente, String tipoCliente) {
        SeguroCartao seguro = new SeguroCartao();
        seguro.setIdCliente(idCliente);
        seguro.setTipoSeguro("VIAGEM");
        seguro.setAtivo(true);

        if (tipoCliente.equalsIgnoreCase("PREMIUM")) {
            seguro.setValorMensal(BigDecimal.ZERO);
        } else {
            seguro.setValorMensal(BigDecimal.valueOf(50));
        }

        seguro.setCobertura(BigDecimal.valueOf(10000)); // Exemplo de cobertura
        return seguroCartaoRepository.save(seguro);
    }

    public SeguroCartao criarSeguroFraude(Long idCliente) {
        SeguroCartao seguro = new SeguroCartao();
        seguro.setIdCliente(idCliente);
        seguro.setTipoSeguro("FRAUDE");
        seguro.setValorMensal(BigDecimal.ZERO); // automático
        seguro.setCobertura(BigDecimal.valueOf(5000));
        seguro.setAtivo(true);
        return seguroCartaoRepository.save(seguro);
    }

    public Optional<SeguroCartao> buscarPorId(Long id) {
        return seguroCartaoRepository.findById(id);
    }
}
