package sn.isi.l3gl.money.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cashin {

    private Long accountId;

    private Double amount;

    private String source;

}
