package br.itau.unibanco.example.transacoes.DemoTransacoes.application.usecase;

import br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.input.TransacaoInputDto;
import br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.output.TransacaoOutputDto;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class SalvarTransacaoUseCase {
    private final TransacaoRepository transacaoRepository;
    public TransacaoOutputDto executar(TransacaoInputDto transacaoInputDto){
        var valorText = transacaoInputDto.getValor().replace(',', '.');
        var dataHora = transacaoInputDto.getDataHora();
        var valor = new BigDecimal(valorText);
        var transacao = new Transacao(valor, dataHora);
        var transacaoSalva = transacaoRepository.salvar(transacao);
        return new TransacaoOutputDto(transacaoSalva);
    }
}
