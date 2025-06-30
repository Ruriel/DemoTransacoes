package br.itau.unibanco.example.transacoes.DemoTransacoes.domain.entities;

import br.itau.unibanco.example.transacoes.DemoTransacoes.api.exceptions.TransacaoInvalidaException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Slf4j
public class Transacao {
    private UUID id;
    private BigDecimal valor;
    private LocalDateTime dataHora;

    public Transacao(BigDecimal valor, LocalDateTime dataHora){
        var now = LocalDateTime.now();
        if(dataHora.isAfter(now)) {
            log.error("A Transação requisitada aconteceu no futuro. DataHora = {}", dataHora);
            throw new TransacaoInvalidaException();
        }
        if(valor.compareTo(BigDecimal.ZERO) < 0) {
            log.error("A Transação requisitada possui valor negativo. Valor = {}", valor);
            throw new TransacaoInvalidaException();
        }
        this.id = UUID.randomUUID();
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public Boolean isInRange(LocalDateTime min, LocalDateTime max){
        return this.dataHora.isAfter(min) && (this.dataHora.isBefore(max) || this.dataHora.isEqual(max));
    }
}
