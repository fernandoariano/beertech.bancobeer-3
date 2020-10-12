package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByHash(UUID hash);
}
