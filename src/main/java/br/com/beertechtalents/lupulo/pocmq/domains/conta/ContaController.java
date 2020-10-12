package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/contas")
@Api(value = "Endpoints para contas")
@RequiredArgsConstructor
@Slf4j
public class ContaController {
    final ContaService contaService;

    @ApiOperation(value = "Cria Conta", nickname = "POST", notes = "Busca o saldo total", response = ResponseEntity.class, tags = {"conta",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @PostMapping
    public ResponseEntity<Conta> createConta(@RequestBody Conta conta) {
        return new ResponseEntity<>(contaService.save(conta), HttpStatus.CREATED);
    }

}
