package br.itau.unibanco.example.transacoes.DemoTransacoes.application.usecase;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LimparTransacoesUseCase {
    private final TransacaoRepository transacaoRepository;
    public boolean executar(){
        return transacaoRepository.removerTodos();
    }
}
