package br.com.beertechtalents.lupulo.pocmq.domains.conta;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    public UUID hash;

}
