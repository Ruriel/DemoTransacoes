package br.itau.unibanco.example.transacoes.DemoTransacoes.api.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class TransacaoInvalidaException extends RuntimeException{
}
