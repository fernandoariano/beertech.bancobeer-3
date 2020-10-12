package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaldoService {

    final SaldoRepository saldoRepository;

    public BigDecimal buscarSaldo(UUID contaHash){
        return saldoRepository.findByContaHash(contaHash);
    }

    public Saldo save(Saldo saldo){
        return saldoRepository.save(saldo);
    }
}
