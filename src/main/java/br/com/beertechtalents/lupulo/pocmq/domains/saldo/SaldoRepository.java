package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SaldoRepository extends JpaRepository<Saldo, UUID> {

    Optional<Saldo> findByContaHash(UUID contaHash);
}
