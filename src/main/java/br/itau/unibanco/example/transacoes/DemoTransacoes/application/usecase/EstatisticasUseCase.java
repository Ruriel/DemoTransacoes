package br.itau.unibanco.example.transacoes.DemoTransacoes.application.usecase;

import br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.output.EstatisticaDto;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EstatisticasUseCase {
    private final TransacaoRepository transacaoRepository;

    @Value("${estatistica.tempoEmSegundos}")
    private Integer tempoEmSegundos;

    public EstatisticaDto executar(){
        var tempo = tempoEmSegundos == null ? 60 : tempoEmSegundos;
        var transacoes = transacaoRepository.buscarPorIntervaloEmSegundos(tempo);
        return new EstatisticaDto(transacoes);
    }
}
