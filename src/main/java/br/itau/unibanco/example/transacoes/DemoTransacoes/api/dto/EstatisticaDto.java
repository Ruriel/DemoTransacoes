package br.itau.unibanco.example.transacoes.DemoTransacoes.api.dto;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.entities.Transacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Getter
@Setter
public class EstatisticaDto {

    private Integer count;

    private BigDecimal sum;

    private BigDecimal min;

    private BigDecimal max;

    @JsonIgnore
    private MathContext mathContext;

    public EstatisticaDto(List<Transacao> transacoes){
        this(transacoes, new MathContext(2));
    }

    public EstatisticaDto(List<Transacao> transacoes, MathContext mathContext){
        this.mathContext = mathContext;
        this.count = transacoes.size();
        this.sum = BigDecimal.ZERO;
        this.min = new BigDecimal(Double.MAX_VALUE);
        this.max = BigDecimal.ZERO;
        transacoes.stream().map(Transacao::getValor).forEach(valor -> {
            this.sum = this.sum.add(valor);
            this.min = valor.min(this.min);
            this.max = valor.max(this.max);
        });
    }

    public BigDecimal getAvg(){
        return this.sum.divide(BigDecimal.valueOf(this.count), this.mathContext);
    }
}
