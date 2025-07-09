package br.itau.unibanco.example.transacoes.DemoTransacoes;

import br.itau.unibanco.example.transacoes.DemoTransacoes.application.exceptions.TransacaoInvalidaException;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Estatistica;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DemoTransacoesApplicationTests {

    private static List<Transacao> generateTransacoes(){
        var now = OffsetDateTime.now();
        var transacao1 = new Transacao(BigDecimal.TEN, now);
        var transacao2 = new Transacao(BigDecimal.valueOf(20), now);
        var transacao3 = new Transacao(BigDecimal.valueOf(30), now);
        return List.of(transacao1, transacao2, transacao3);
    }

    @Test
    void whenTransacaoValida_ThenAssertTransacao() {
        var valor = BigDecimal.TEN;
        var dataHora = OffsetDateTime.now();
        try {
            var transacao = new Transacao(valor, dataHora);
        } catch (TransacaoInvalidaException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void whenTransacaoNoFuturo_ThenThrowsTransacaoInvalidaException() {
        var valor = new BigDecimal("100");
        var dataHora = OffsetDateTime.now().plusDays(1);
        assertThrows(TransacaoInvalidaException.class, () -> new Transacao(valor, dataHora));
    }

    @Test
    void whenTransacaoValorNegativo_ThenThrowsTransacaoInvalidaException() {
        var valor = new BigDecimal("-100");
        var dataHora = OffsetDateTime.now();
        assertThrows(TransacaoInvalidaException.class, () -> new Transacao(valor, dataHora));
    }

    @Test
    void whenTransacaoIsInRange_ThenReturnsTrue() {
        var valor = BigDecimal.TEN;
        var now = OffsetDateTime.now();
        var min = now.minusSeconds(60);
        var dataHora = OffsetDateTime.now();
        var transacao = new Transacao(valor, dataHora);
        assertEquals(true, transacao.isInRange(min, now));
    }

    @Test
    void whenTransacaoIsNotInRange_ThenReturnsFalse() {
        var valor = BigDecimal.TEN;
        var now = OffsetDateTime.now();
        var min = now.minusSeconds(60);
        var dataHora = now.minusSeconds(61);
        var transacao = new Transacao(valor, dataHora);
        assertEquals(false, transacao.isInRange(min, now));
    }

    @Test
    void whenTransacoesIsEmpty_ThenAssertCountIsZero() {
        var transacoes = new ArrayList<Transacao>();
        var estatistica = new Estatistica(transacoes);
        assertEquals(0, estatistica.getCount());
    }

    @Test
    void whenTransacoesIsEmpty_ThenAssertMinIsZero() {
        var transacoes = new ArrayList<Transacao>();
        var estatistica = new Estatistica(transacoes);
        assertEquals(BigDecimal.ZERO, estatistica.getMin());
    }

    @Test
    void whenTransacoesIsEmpty_ThenAssertMaxIsZero() {
        var transacoes = new ArrayList<Transacao>();
        var estatistica = new Estatistica(transacoes);
        assertEquals(BigDecimal.ZERO, estatistica.getMax());
    }

    @Test
    void whenTransacoesIsEmpty_ThenAssertAvgIsZero() {
        var transacoes = new ArrayList<Transacao>();
        var estatistica = new Estatistica(transacoes);
        assertEquals(BigDecimal.ZERO, estatistica.getAvg());
    }

    @Test
    void whenTransacoesIsNotEmpty_ThenAssertCountIsThree(){
        var transacoes = generateTransacoes();
        var estatistica = new Estatistica(transacoes);
        assertEquals(3, estatistica.getCount());
    }

    @Test
    void whenTransacoesIsNotEmpty_ThenAssertMinIsTen(){
        var transacoes = generateTransacoes();
        var estatistica = new Estatistica(transacoes);
        assertEquals(BigDecimal.TEN, estatistica.getMin());
    }

    @Test
    void whenTransacoesIsNotEmpty_ThenAssertMaxIsThirty(){
        var transacoes = generateTransacoes();
        var estatistica = new Estatistica(transacoes);
        assertEquals(BigDecimal.valueOf(30), estatistica.getMax());
    }

    @Test
    void whenTransacoesIsNotEmpty_ThenAssertAvgIsTwenty(){
        var transacoes = generateTransacoes();
        var estatistica = new Estatistica(transacoes);
        assertEquals(BigDecimal.valueOf(20), estatistica.getAvg());
    }
}
