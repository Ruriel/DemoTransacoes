package br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaDto {

    private Integer count;

    private BigDecimal sum;

    private BigDecimal min;

    private BigDecimal max;

    private BigDecimal avg;

}
