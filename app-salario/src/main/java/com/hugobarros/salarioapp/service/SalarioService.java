package com.hugobarros.salarioapp.service;

import com.hugobarros.salarioapp.model.financeiro.*;
import com.hugobarros.salarioapp.model.pessoal.Pessoa;
import com.hugobarros.salarioapp.repository.*;
import com.hugobarros.salarioapp.dto.PessoaSalarioDTO;
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
    private static final int MAX_THREADS = 6;

    /**
     * Inicia o processamento em segundo plano.
     */
    public void recalcularSalariosAsync(Runnable onFinish) {
        new Thread(() -> { processarEmLotes();
            if (onFinish != null) onFinish.run();
        }).start();
    }

    /**
     * Processa todas as pessoas dividindo em grupos controlados por threads.
     */
    private void processarEmLotes() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        int total = pessoas.size();

        System.out.println("Iniciando processamento assíncrono para " + total + " registros...");

        // Dividir as pessoas em blocos proporcionais ao número de threads
        int lote = (int) Math.ceil((double) total / MAX_THREADS);

        Thread[] threads = new Thread[MAX_THREADS];

        for (int i = 0; i < MAX_THREADS; i++) {
            int inicio = i * lote;
            int fim = Math.min(inicio + lote, total);
            if (inicio >= fim) break;

            List<Pessoa> sublista = pessoas.subList(inicio, fim);
            threads[i] = new Thread(() -> processarGrupo(sublista), "Thread-Salario-" + (i + 1));
            threads[i].start();
        }

        // Aguarda todas as threads terminarem
        for (Thread t : threads) {
            if (t != null) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        System.out.println("Processamento assíncrono concluído.");
    }

    private void processarGrupo(List<Pessoa> pessoas) {
        for (Pessoa pessoa : pessoas) {
            recalcularPessoa(pessoa);
        }
    }

    /**
     * Processa individualmente a pessoa.
     */
    @Transactional
    public void recalcularPessoa(Pessoa pessoa) {
        var cargo = pessoa.getCargo();
        if (cargo == null) return;

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

        PessoaSalarioConsolidado consolidado =
                consolidadoRepository.findById(pessoa.getId())
                        .orElse(new PessoaSalarioConsolidado());

        consolidado.setPessoaId(pessoa.getId());
        consolidado.setNomePessoa(pessoa.getNome());
        consolidado.setNomeCargo(cargo.getNome());
        consolidado.setSalario(total);

        consolidadoRepository.save(consolidado);
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
