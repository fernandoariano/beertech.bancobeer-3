package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@ExtendWith({MockitoExtension.class})
public class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void saveContaTest() {
        Conta conta = Conta.builder()
                .id(new Random().nextLong())
                .hash(UUID.randomUUID())
                .build();

        Mockito.when(contaRepository.save(Mockito.any(Conta.class)))
                .then(c -> c.getArgument(0));

        Conta contaSaved = contaService.save(conta);
        Assertions.assertEquals(conta.getId(), contaSaved.getId());
        Assertions.assertEquals(conta.getHash(), contaSaved.getHash());
    }

    @Test
    void findContaByIdTest() {
        Conta conta = Conta.builder()
                .id(new Random().nextLong())
                .hash(UUID.randomUUID())
                .build();

        Mockito.when(contaRepository.findById(Mockito.any(Long.class)))
                .then(c -> Optional.of(conta));

        Conta contaFound = contaService.findById(conta.getId());
        Assertions.assertEquals(conta.getId(), contaFound.getId());
        Assertions.assertEquals(conta.getHash(), contaFound.getHash());
    }

    @Test
    void findContaByHashTest() {
        Conta conta = Conta.builder()
                .id(new Random().nextLong())
                .hash(UUID.randomUUID())
                .build();

        Mockito.when(contaRepository.findByHash(Mockito.any(UUID.class)))
                .then(c -> Optional.of(conta));

        Conta contaFound = contaService.findByHash(conta.getHash());
        Assertions.assertEquals(conta.getId(), contaFound.getId());
        Assertions.assertEquals(conta.getHash(), contaFound.getHash());
    }

    @Test
    void findContaByHashNotFoundTest() {
        Conta conta = Conta.builder()
                .id(new Random().nextLong())
                .hash(UUID.randomUUID())
                .build();

        Mockito.when(contaRepository.findByHash(Mockito.any(UUID.class)))
                .then(c -> Optional.empty());

        Assertions.assertThrows(HttpClientErrorException.NotFound.class,
                () -> contaService.findByHash(conta.getHash()));
    }


    @Test
    void findContaByIdNotFoundTest() {
        Conta conta = Conta.builder()
                .id(new Random().nextLong())
                .hash(UUID.randomUUID())
                .build();

        Mockito.when(contaRepository.findById(Mockito.any(Long.class)))
                .then(c -> Optional.empty());

        Assertions.assertThrows(HttpClientErrorException.NotFound.class,
                () -> contaService.findById(conta.getId()));
    }
}
