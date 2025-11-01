package com.hugobarros.salarioapp.service;

import com.hugobarros.salarioapp.dto.PessoaSalarioDTO;
import com.hugobarros.salarioapp.model.financeiro.*;
import com.hugobarros.salarioapp.model.pessoal.Pessoa;
import com.hugobarros.salarioapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalarioService {

    private final PessoaRepository pessoaRepository;
    private final CargoVencimentoRepository cargoVencimentoRepository;
    private final PessoaSalarioConsolidadoRepository consolidadoRepository;

    @Transactional
    public void recalcularSalarios() {
        List<Pessoa> pessoas = pessoaRepository.findAll();

        for (Pessoa pessoa : pessoas) {
            var cargo = pessoa.getCargo();
            if (cargo == null) continue;

            List<CargoVencimento> vencimentosCargo = cargoVencimentoRepository.findByCargoId(cargo.getId());
            Double total = 0.00;

            for (CargoVencimento cargoV : vencimentosCargo) {
                Vencimento vencimento = cargoV.getVencimento();
                if (vencimento == null || vencimento.getValor() == null || vencimento.getTipo() == null) continue;

                if (vencimento.getTipo() == TipoVencimento.CREDITO) {
                    total += vencimento.getValor();
                } else if (vencimento.getTipo() == TipoVencimento.DEBITO) {
                    total -= vencimento.getValor();
                }
            }

            PessoaSalarioConsolidado consolidado = consolidadoRepository.findById(pessoa.getId())
                                                                        .orElse(new PessoaSalarioConsolidado());

            consolidado.setPessoaId(pessoa.getId());
            consolidado.setNomePessoa(pessoa.getNome());
            consolidado.setNomeCargo(cargo.getNome());
            consolidado.setSalario(total);

            consolidadoRepository.save(consolidado);
        }
    }

    public List<PessoaSalarioDTO> listarConsolidado() {
        List<PessoaSalarioConsolidado> entidades = consolidadoRepository.findAll();

        return entidades.stream()
                .map(psc -> new PessoaSalarioDTO(
                        psc.getPessoaId(),
                        psc.getNomePessoa(),
                        psc.getNomeCargo(),
                        psc.getSalario() != null ? psc.getSalario().doubleValue() : 0.00
                ))
                .toList();
    }
}
