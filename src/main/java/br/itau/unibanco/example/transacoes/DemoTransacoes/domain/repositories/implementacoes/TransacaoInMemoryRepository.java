package br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repositories.implementacoes;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.entities.Transacao;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repositories.interfaces.TransacaoRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransacaoInMemoryRepository implements TransacaoRepository {
    private final Map<String, Transacao> transacaoMap;

    public TransacaoInMemoryRepository() {
        this.transacaoMap = new HashMap<>();
    }


    @Override
    public Transacao salvar(Transacao transacao) {
        var id = transacao.getId().toString();
        transacaoMap.put(id, transacao);
        return transacao;
    }

    @Override
    public Boolean removerTodos() {
        try {
            transacaoMap.clear();
            return true;
        }
        catch (UnsupportedOperationException exception){
            return false;
        }
    }

    @Override
    public List<Transacao> buscarPorTempo(Integer tempoEmSegundos) {
        var max = LocalDateTime.now();
        var min = max.minusSeconds(tempoEmSegundos);
        return this.transacaoMap.values().stream().filter(transacao -> transacao.isInRange(min, max)).toList();
    }
}
