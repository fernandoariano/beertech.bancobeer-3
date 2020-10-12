package br.com.beertechtalents.lupulo.pocmq.domains.transacao;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@Api(value = "Endpoints para transações")
@RequiredArgsConstructor
@Slf4j
public class TransacaoController {

    final TransacaoService transacaoService;


    @ApiOperation(value = "Adiciona uma nova transacao", nickname = "POST", notes = "", tags = {"transacao",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void novaOperacao(@ApiParam(value = "Tool object that needs to be added", required = true) @RequestBody Transacao body) {

        switch (body.getTipo()){
            case SAQUE:
                body.setValor(body.getValor().abs().negate());
                transacaoService.salvarTransacao(body);
                break;

            case DEPOSITO:
                body.setValor(body.getValor().abs());
                transacaoService.salvarTransacao(body);
                break;
            default:
                break;
        }
    }

    @PostMapping(path = "/transferencias", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void novaTransferencia(@ApiParam(value = "Transferencia object must be provided", required = true) @RequestBody Transferencia transferenciaRequest){
        transacaoService.transferir(transferenciaRequest);
    }
}

