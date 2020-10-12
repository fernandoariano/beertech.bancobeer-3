package br.com.beertechtalents.lupulo.pocmq.domains.transacao;

import br.com.beertechtalents.lupulo.pocmq.domains.saldo.Saldo;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.SaldoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    final SaldoService saldoService;
    final TransacaoRepository transacaoRepository;

    public void salvarTransacao(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    @Transactional
    public void transferir(Transferencia transferencia) {
        BigDecimal saldoContaDe = saldoService.buscarSaldo(transferencia.hashContaDe);
        BigDecimal saldoContaPara = saldoService.buscarSaldo(transferencia.hashContaPara);

        Transacao saque = Transacao.builder()
                .tipo(TipoTransacao.TRANSFERENCIA)
                .valor(transferencia.valor.negate())
                .contaHash(transferencia.hashContaDe)
                .build();

        Transacao deposito = Transacao.builder()
                .tipo(TipoTransacao.TRANSFERENCIA)
                .valor(transferencia.valor)
                .contaHash(transferencia.hashContaPara)
                .build();

        Saldo novoSaldoDe = Saldo.builder()
                .contaHash(transferencia.hashContaDe)
                .valor(saldoContaDe.subtract(transferencia.valor))
                .build();
        Saldo novoSaldoPara = Saldo.builder()
                .contaHash(transferencia.hashContaPara)
                .valor(saldoContaPara.add(transferencia.valor))
                .build();

        transacaoRepository.save(saque);
        transacaoRepository.save(deposito);
        saldoService.save(novoSaldoDe);
        saldoService.save(novoSaldoPara);
    }

}
