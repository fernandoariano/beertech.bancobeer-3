package br.com.beertechtalents.lupulo.pocmq.domains.transacao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByContaHash(UUID contaHash);
}
