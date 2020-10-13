package br.com.beertechtalents.lupulo.pocmq.domains.transacao;

import br.com.beertechtalents.lupulo.pocmq.domains.conta.Conta;
import br.com.beertechtalents.lupulo.pocmq.domains.conta.ContaService;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.Saldo;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.SaldoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final SaldoService saldoService;
    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;

    public void salvarTransacao(Transacao transacao) {
        contaService.findByHash(transacao.getContaHash());
        Saldo saldo = saldoService.buscarSaldo(transacao.getContaHash());

        Transacao transacaoSaved = transacaoRepository.save(transacao);
        saldo.setValor(saldo.getValor().add(transacaoSaved.getValor()));
        saldoService.save(saldo);
    }

    @Transactional
    public void transferir(Transferencia transferencia) {
        Saldo saldoContaDe = saldoService.buscarSaldo(transferencia.getHashContaDe());
        Saldo saldoContaPara = saldoService.buscarSaldo(transferencia.getHashContaPara());


        Transacao credito = Transacao.builder()
                .tipo(TipoTransacao.TRANSFERENCIA)
                .valor(transferencia.getValor().negate())
                .contaHash(transferencia.getHashContaDe())
                .build();

        Transacao debito = Transacao.builder()
                .tipo(TipoTransacao.TRANSFERENCIA)
                .valor(transferencia.getValor())
                .contaHash(transferencia.getHashContaPara())
                .build();

        Saldo novoSaldoDe = Saldo.builder()
                .contaHash(transferencia.getHashContaDe())
                .valor(saldoContaDe.getValor().subtract(transferencia.getValor()))
                .build();
        Saldo novoSaldoPara = Saldo.builder()
                .contaHash(transferencia.getHashContaPara())
                .valor(saldoContaPara.getValor().add(transferencia.getValor()))
                .build();

        transacaoRepository.save(credito);
        transacaoRepository.save(debito);
        saldoService.save(novoSaldoDe);
        saldoService.save(novoSaldoPara);

    }

    public List<Transacao> listarTransacoesPorContaHash(UUID contaHash) {
        //Se não existir, uma exceção será lançada
        contaService.findByHash(contaHash);
        return transacaoRepository.findAllByContaHash(contaHash);
    }

}
