package br.com.beertechtalents.lupulo.pocmq.domains.transacao;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
@Api(value = "Endpoints para transações", tags = {"Transacoes",})
@RequiredArgsConstructor
@Slf4j
public class TransacaoController {

    final TransacaoService transacaoService;


    @ApiOperation(value = "Adiciona uma nova transacao", nickname = "POST", notes = "", tags = {"Transacoes",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operacao realizada")})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void novaOperacao(@ApiParam(value = "Transaction object that needs to be added", required = true) @RequestBody TransacaoRequest body) {

        Transacao transacao = Transacao.builder().tipo(body.getTipo()).contaHash(body.getContaHash()).valor(body.getValor()).build();

        switch (transacao.getTipo()) {
            case SAQUE:
                transacao.setValor(transacao.getValor().abs().negate());
                transacaoService.salvarTransacao(transacao);
                break;

            case DEPOSITO:
                transacao.setValor(transacao.getValor().abs());
                transacaoService.salvarTransacao(transacao);
                break;
            default:
                break;
        }
    }

    @ApiOperation(value = "Adiciona uma nova transferencia", nickname = "POST", notes = "Realiza uma transferência entre contas", tags = {"Transacoes",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Transferencia realizada")})
    @PostMapping(path = "/transferencias", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> novaTransferencia(@ApiParam(value = "Transferencia object must be provided", required = true) @RequestBody Transferencia transferenciaRequest) {
        transacaoService.transferir(transferenciaRequest);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Listar transacoes por hash", nickname = "GET", notes = "Lista as transações de uma conta pelo hash", tags = {"Transacoes",})
    @GetMapping(path = "/contas/{hash}")
    public ResponseEntity<Object> getTransacoes(@ApiParam(value = "hash da conta a ser buscado", required = true) @PathVariable UUID hash){
        List<Transacao> transacaoList = transacaoService.listarTransacoesPorContaHash(hash);
        return ResponseEntity.ok(transacaoList);
    }
}

