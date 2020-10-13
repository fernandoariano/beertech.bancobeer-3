package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true, nullable = false, columnDefinition = "VARBINARY(16)" )
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID hash;

    @PrePersist
    public void autofill() {
        this.setHash(UUID.randomUUID());
    }

}
