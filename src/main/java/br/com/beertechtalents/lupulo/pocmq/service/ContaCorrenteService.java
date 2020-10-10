package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.repository.ContaCorrenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {

    final ContaCorrenteRepository contaCorrenteRepository;

    public BigDecimal buscarSaldo(String idContaCorrente){
        System.out.println("id: " + idContaCorrente);
        return contaCorrenteRepository.somaSaldo(UUID.fromString(idContaCorrente));
    }

    public String salvarContaCorrente(ContaCorrente contaCorrente){
        return contaCorrenteRepository.saveAndFlush(contaCorrente).getIdContaCorrente().toString();
    }
}
