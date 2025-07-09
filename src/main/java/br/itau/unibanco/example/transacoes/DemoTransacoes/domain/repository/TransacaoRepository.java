package br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Estatistica;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;

import java.util.List;

public interface TransacaoRepository {

    Transacao salvar(Transacao transacao);

    Boolean removerTodos();

    List<Transacao> buscarPorIntervaloEmSegundos(Integer tempoEmSegundos);

    Estatistica buscarEstatisticaPorIntervaloEmSegundos(Integer tempoEmSegundos);
}
