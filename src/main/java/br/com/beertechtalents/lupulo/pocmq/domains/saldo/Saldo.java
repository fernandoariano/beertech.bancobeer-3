package br.com.beertechtalents.lupulo.pocmq.domains.saldo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Saldo {

    @Id
    @Column(columnDefinition = "VARBINARY(16)")
    private UUID contaHash;

    private BigDecimal valor;
}
