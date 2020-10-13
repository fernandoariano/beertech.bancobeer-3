package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.domains.conta.Conta;
import br.com.beertechtalents.lupulo.pocmq.domains.conta.ContaService;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.Saldo;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.SaldoService;
import br.com.beertechtalents.lupulo.pocmq.domains.transacao.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ContaService contaService;

    @Mock
    private SaldoService saldoService;

    @Test
    public void saveTest() {
        Transacao transacao = new Transacao();
        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacao.setValor(BigDecimal.TEN);
        transacao.setContaHash(UUID.randomUUID());

        Mockito.when(contaService.findByHash(Mockito.any(UUID.class))).then(c -> Conta.builder().
                hash(UUID.randomUUID()).
                id(new Random().nextLong())
                .build());

        Mockito.when(saldoService.buscarSaldo(Mockito.any(UUID.class))).then(s ->
                Saldo.builder().valor(BigDecimal.TEN).contaHash(transacao.getContaHash()).build());

        Mockito.when(saldoService.save(Mockito.any(Saldo.class))).then(s ->
                Saldo.builder().valor(BigDecimal.TEN).contaHash(transacao.getContaHash()).build());


        Mockito.when(transacaoRepository.save(Mockito.any(Transacao.class))).then(i -> {
            Transacao t = (Transacao) i.getArguments()[0];
            t.setId(1l);
            t.setDatahora(new Timestamp(10000l));
            return t;
        });

        transacaoService.salvarTransacao(transacao);
    }

    @Test
    public void transferTest() {
        UUID hashContaDe = UUID.randomUUID();
        UUID hashContaPara = UUID.randomUUID();

        Transferencia transferencia = Transferencia.builder()
                .valor(BigDecimal.TEN)
                .hashContaDe(hashContaDe)
                .hashContaPara(hashContaPara)
                .build();

        Mockito.when(saldoService.buscarSaldo(Mockito.any(UUID.class))).then(s ->
                Saldo.builder().valor(BigDecimal.TEN).contaHash(transferencia.getHashContaPara()).build());

        Mockito.when(saldoService.save(Mockito.any(Saldo.class))).then(s -> s.getArgument(0));

        Mockito.when(transacaoRepository.save(Mockito.any(Transacao.class))).then(t -> {
            Transacao transacao = (Transacao) t.getArgument(0);
            transacao.setId(new Random().nextLong());
            transacao.setDatahora(new Timestamp(System.currentTimeMillis()));
            return transacao;
        });

        transacaoService.transferir(transferencia);
    }

    @Test
    void findTransacoesByContaTest() {

        Conta conta = Conta.builder()
                .id(new Random().nextLong())
                .hash(UUID.randomUUID())
                .build();


        List<Transacao> expectedList = Arrays.asList(
                Transacao.builder()
                        .valor(BigDecimal.TEN)
                        .tipo(TipoTransacao.TRANSFERENCIA)
                        .contaHash(conta.getHash())
                        .build(),
                Transacao.builder().
                        valor(BigDecimal.TEN)
                        .tipo(TipoTransacao.DEPOSITO)
                        .contaHash(conta.getHash())
                        .build());

        Mockito.when(transacaoRepository.findAllByContaHash(Mockito.any(UUID.class))).then(t -> expectedList);

        Mockito.when(contaService.findByHash(Mockito.any(UUID.class)))
                .then(c -> conta);

        List<Transacao> transacaoList = transacaoService.listarTransacoesPorContaHash(conta.getHash());

        Assertions.assertIterableEquals(expectedList, transacaoList);
    }

}

