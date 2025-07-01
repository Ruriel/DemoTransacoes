package br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model;

import br.itau.unibanco.example.transacoes.DemoTransacoes.application.exceptions.TransacaoInvalidaException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class Transacao {
    private UUID id;
    private BigDecimal valor;
    private OffsetDateTime dataHora;

    public Transacao(BigDecimal valor, OffsetDateTime dataHora){
        var now = OffsetDateTime.now();
        if(dataHora.isAfter(now)) {
            log.error("A Transação requisitada aconteceu no futuro. DataHora = {}", dataHora);
            throw new TransacaoInvalidaException();
        }
        if(valor.compareTo(BigDecimal.ZERO) < 0) {
            log.error("A Transação requisitada possui valor negativo. Valor = {}", valor);
            throw new TransacaoInvalidaException();
        }
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Boolean isInRange(OffsetDateTime min, OffsetDateTime max){
        return this.dataHora.isAfter(min) && (this.dataHora.isBefore(max) || this.dataHora.isEqual(max));
    }
}
