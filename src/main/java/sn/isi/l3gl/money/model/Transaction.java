package sn.isi.l3gl.money.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private Long fromAccountId;

    private Long toAccountId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Type operationType;

    private String source;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String transactionId;

    private Instant transactionDate = Instant.now();

    public enum Type {
        CASHIN,
        TRANSFER
    }

    public enum Status {
        PENDING,
        SUCCESS,
        FAILED
    }

}
