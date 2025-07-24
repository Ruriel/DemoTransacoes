package br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoInputDto {

    @NotBlank
    @Pattern(regexp = "-?\\d+([.,]\\d{2})?")
    private String valor;

    @NotBlank
    @PastOrPresent
    private OffsetDateTime dataHora;
}
