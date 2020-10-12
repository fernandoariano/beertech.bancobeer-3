package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/saldos")
@Api(value = "Endpoints para saldos")
@RequiredArgsConstructor
@Slf4j
public class SaldoController {

    final SaldoService saldoService;

    @ApiOperation(value = "Busca saldo total", nickname = "GET", notes = "Busca o saldo total", response = BigDecimal.class, tags = {"tool",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = BigDecimal.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(path = "/{hash}")
    public ResponseEntity<BigDecimal> getSaldoByHash(@PathVariable UUID hash) {
        return new ResponseEntity<>(saldoService.buscarSaldo(hash), HttpStatus.OK);
    }
}
