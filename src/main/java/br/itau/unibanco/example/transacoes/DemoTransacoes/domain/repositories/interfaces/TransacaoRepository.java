package br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repositories.interfaces;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.entities.Transacao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository {

    Transacao salvar(Transacao transacao);

    Boolean removerTodos();

    List<Transacao> buscarPorTempo(Integer tempoEmSegundos);
}
