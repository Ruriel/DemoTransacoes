package br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@Setter
public class Estatistica {

    private Integer count;

    private BigDecimal sum;

    private BigDecimal min;

    private BigDecimal max;

    public Estatistica(List<Transacao> transacoes){
        this.count = transacoes.size();
        this.sum = BigDecimal.ZERO;
        this.min = this.count == 0 ? BigDecimal.ZERO : transacoes.getFirst().getValor();
        this.max = this.count == 0 ? BigDecimal.ZERO : transacoes.getFirst().getValor();
        transacoes.forEach(transacao -> {
            var valor = transacao.getValor();
            this.sum = this.sum.add(valor);
            this.min = valor.min(this.min);
            this.max = valor.max(this.max);
        });
    }

    public BigDecimal getAvg(){
        if(this.count == 0)
            return BigDecimal.ZERO;
        return this.sum.divide(BigDecimal.valueOf(this.count), RoundingMode.HALF_DOWN);
    }
}
