package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SaldoServiceTest {

    @InjectMocks
    private SaldoService saldoService;

    @Mock
    private SaldoRepository saldoRepository;

    @Test
    void saveTest() {
        Saldo saldo = Saldo.builder()
                .contaHash(UUID.randomUUID())
                .valor(BigDecimal.TEN)
                .build();

        Mockito.when(saldoRepository.save(Mockito.any(Saldo.class))).then(s -> s.getArgument(0));

        Saldo saldoSaved = saldoService.save(saldo);

        Assertions.assertEquals(saldo.getValor(), saldoSaved.getValor());
        Assertions.assertEquals(saldo.getContaHash(), saldoSaved.getContaHash());
    }

    @Test
    void findByHashTest() {
        Saldo saldo = Saldo.builder()
                .contaHash(UUID.randomUUID())
                .valor(BigDecimal.TEN)
                .build();

        Mockito.when(saldoRepository.findByContaHash(Mockito.any(UUID.class))).then(s -> Optional.of(saldo));

        Saldo saldoFound = saldoService.buscarSaldo(saldo.getContaHash());

        Assertions.assertEquals(saldo.getContaHash(), saldoFound.getContaHash());
        Assertions.assertEquals(saldo.getValor(), saldoFound.getValor());
    }

    @Test
    void findByHashTestNotFoundException() {
        Saldo saldo = Saldo.builder()
                .contaHash(UUID.randomUUID())
                .valor(BigDecimal.TEN)
                .build();

        Mockito.when(saldoRepository.findByContaHash(Mockito.any(UUID.class)))
                .then(s -> Optional.empty());

        Assertions.assertThrows(HttpClientErrorException.NotFound.class,
                () -> saldoService.buscarSaldo(saldo.getContaHash()));
    }
}
