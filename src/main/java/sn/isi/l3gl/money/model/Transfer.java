package sn.isi.l3gl.money.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    private Long from;

    private Long to;

    private Double amount;

}
