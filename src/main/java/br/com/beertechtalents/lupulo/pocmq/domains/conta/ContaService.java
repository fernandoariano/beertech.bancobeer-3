package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import br.com.beertechtalents.lupulo.pocmq.domains.saldo.Saldo;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.SaldoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

    final ContaRepository contaRepository;
    final SaldoService saldoService;

    @Transactional
    public Conta save(Conta conta) {
        Conta contaSaved = contaRepository.save(conta);
        return contaSaved;
    }

    public Conta findById(Long id) {
        Optional<Conta> conta =  contaRepository.findById(id);
        if (!conta.isPresent()) {
            throw HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Conta não encontrada", HttpHeaders.EMPTY, null, null);
        } else {
            return conta.get();
        }
    }

    public Conta findByHash(UUID hash) {

        Optional<Conta> conta = contaRepository.findByHash(hash);
        if (!conta.isPresent()) {
            throw HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Conta não encontrada", HttpHeaders.EMPTY, null, null);
        } else {
            return conta.get();
        }
    }
}
