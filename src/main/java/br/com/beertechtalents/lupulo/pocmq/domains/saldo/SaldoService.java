package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaldoService {

    final SaldoRepository saldoRepository;

    public Saldo buscarSaldo(UUID contaHash){
        Optional<Saldo> saldo = saldoRepository.findByContaHash(contaHash);
        if(!saldo.isPresent()){
            throw HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Saldo não encontrado para essa conta", HttpHeaders.EMPTY, null, null);
        } else {
            return saldo.get();
        }
    }

    public Saldo save(Saldo saldo){
        return saldoRepository.save(saldo);
    }
}
