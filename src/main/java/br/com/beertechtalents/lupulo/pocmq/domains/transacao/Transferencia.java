package br.com.beertechtalents.lupulo.pocmq.domains.transacao;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Transferencia {

    public BigDecimal valor;

    public UUID hashContaDe;

    public UUID hashContaPara;
}
