package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import br.com.beertechtalents.lupulo.pocmq.domains.saldo.Saldo;
import br.com.beertechtalents.lupulo.pocmq.domains.saldo.SaldoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/contas")
@Api(value = "Endpoints para contas", tags = {"Contas",})
@RequiredArgsConstructor
@Slf4j
public class ContaController {
    final ContaService contaService;
    final SaldoService saldoService;

    @ApiOperation(value = "Cria Conta", nickname = "POST", notes = "Busca o saldo total", response = ResponseEntity.class, tags = {"Contas",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @PostMapping
    public ResponseEntity<ContaResponse> createConta() {
        Conta contaSaved = contaService.save(new Conta());

        Saldo newSaldo = Saldo.builder()
                .valor(BigDecimal.ZERO)
                .contaHash(contaSaved.getHash())
                .build();

        Saldo saldoSaved = saldoService.save(newSaldo);

        ContaResponse response = ContaResponse.builder()
                .hash(contaSaved.getHash())
                .id(contaSaved.getId())
                .saldo(saldoSaved.getValor())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "Consular Conta", nickname = "GET", notes = "Consulta a conta pelo hash", response = Conta.class, tags = {"Contas",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Conta.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(path = "/{hash}")
    public ResponseEntity<Object> getContaByHash(@ApiParam(value = "hash da conta a ser buscada", required = true) @PathVariable UUID hash) {
        Conta conta = contaService.findByHash(hash);

        return ResponseEntity.ok(conta);

    }

}
