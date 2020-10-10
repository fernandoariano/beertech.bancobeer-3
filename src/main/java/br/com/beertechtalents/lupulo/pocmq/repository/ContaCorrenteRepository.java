package br.com.beertechtalents.lupulo.pocmq.repository;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.UUID;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {

    @Query("SELECT SUM(c.saldo) FROM ContaCorrente c WHERE c.idContaCorrente = ?1")
    BigDecimal somaSaldo(UUID idContaCorrente);

    //89affedd-b860-4f7e-9379-8b90e9c55a8b
}
