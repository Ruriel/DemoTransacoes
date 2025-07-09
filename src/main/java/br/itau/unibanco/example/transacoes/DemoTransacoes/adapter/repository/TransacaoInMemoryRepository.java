package br.itau.unibanco.example.transacoes.DemoTransacoes.adapter.repository;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Estatistica;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Slf4j
public class TransacaoInMemoryRepository implements TransacaoRepository {
    private final Map<String, Transacao> transacaoMap;

    public TransacaoInMemoryRepository() {
        this.transacaoMap = new HashMap<>();
    }


    @Override
    public Transacao salvar(Transacao transacao) {
        var id = UUID.randomUUID();
        transacao.setId(id);
        transacaoMap.put(id.toString(), transacao);
        log.info("{}:/{{},{}/}", id, transacao.getValor(), transacao.getDataHora());
        log.info("Total de elementos: {}", transacaoMap.size());
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
    public List<Transacao> buscarPorIntervaloEmSegundos(Integer tempoEmSegundos) {
        var max = OffsetDateTime.now();
        var min = max.minusSeconds(tempoEmSegundos);
        var transacoes = this.transacaoMap.values();
        return transacoes.stream().filter(transacao -> transacao.isInRange(min, max)).toList();
    }

    @Override
    public Estatistica buscarEstatisticaPorIntervaloEmSegundos(Integer tempoEmSegundos) {
        var transacoes = buscarPorIntervaloEmSegundos(tempoEmSegundos);
        return new Estatistica(transacoes);
    }
}
