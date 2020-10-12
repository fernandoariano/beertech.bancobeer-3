package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.UUID;

public interface SaldoRepository extends JpaRepository<Saldo, UUID> {

    BigDecimal findByContaHash(UUID contaHash);
}
