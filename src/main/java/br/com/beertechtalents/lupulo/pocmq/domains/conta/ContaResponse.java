package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ContaResponse {
    private Long id;

    private UUID hash;

    private BigDecimal saldo;

}
