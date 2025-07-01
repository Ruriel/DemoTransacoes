package br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.output;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
@NoArgsConstructor
public class TransacaoOutputDto {
    private String id;
    private String valor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dataHora;

    public TransacaoOutputDto(Transacao transacao){
        this.id = transacao.getId() == null ? null : transacao.getId().toString();
        this.valor = transacao.getValor() == null ? null : transacao.getValor().toString();
        this.dataHora = transacao.getDataHora();
    }
}
