package br.itau.unibanco.example.transacoes.DemoTransacoes.adapter.controller;

import br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.input.TransacaoInputDto;
import br.itau.unibanco.example.transacoes.DemoTransacoes.application.usecase.SalvarTransacaoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class TransacaoController {

    private final SalvarTransacaoUseCase salvarTransacaoUseCase;

    @PostMapping("/transacao")
    public ResponseEntity<?> salvar(@RequestBody TransacaoInputDto transacaoInputDto){
        var valorText = transacaoInputDto.getValor();
        var dataHora = transacaoInputDto.getDataHora();
        var transacaoOutputDto = salvarTransacaoUseCase.executar(valorText, dataHora);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoOutputDto);
    }
}
