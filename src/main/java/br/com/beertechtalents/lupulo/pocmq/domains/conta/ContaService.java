package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

    final ContaRepository contaRepository;

    public Conta save(Conta conta) {
        return contaRepository.save(conta);
    }

    public Optional<Conta> findById(Long id){
        return contaRepository.findById(id);
    }

    public Optional<Conta> findByHash(UUID hash){
        return contaRepository.findByHash(hash);
    }
}
