package br.itau.unibanco.example.transacoes.DemoTransacoes;

import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoTransacoesApplication.class)
@AutoConfigureMockMvc
public class EstatisticaUseCaseIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        transacaoRepository.removerTodos();
    }

    @Test
    void shouldReturnEstatisticas() throws Exception {
        var now = OffsetDateTime.now();
        var transacao1 = new Transacao(BigDecimal.TEN, now);
        var transacao2 = new Transacao(BigDecimal.valueOf(20), now);
        var transacao3 = new Transacao(BigDecimal.valueOf(30), now);
        transacaoRepository.salvar(transacao1);
        transacaoRepository.salvar(transacao2);
        transacaoRepository.salvar(transacao3);

        mockMvc.perform(
                    get("/estatistica")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.sum").value(60))
                .andExpect(jsonPath("$.min").value(10))
                .andExpect(jsonPath("$.max").value(30))
                .andExpect(jsonPath("$.avg").value(20))
                .andReturn();
    }

    @Test
    void shouldReturnEmptyEstatisticas() throws Exception {
        mockMvc.perform(
                        get("/estatistica")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0))
                .andExpect(jsonPath("$.min").value(0))
                .andExpect(jsonPath("$.max").value(0))
                .andExpect(jsonPath("$.avg").value(0))
                .andReturn();
    }

    @Test
    void shouldReturnPartialEstatisticas() throws Exception {
        var now = OffsetDateTime.now();
        var transacao1 = new Transacao(BigDecimal.TEN, now.minusDays(1));
        var transacao2 = new Transacao(BigDecimal.valueOf(20), now);
        var transacao3 = new Transacao(BigDecimal.valueOf(30), now);
        transacaoRepository.salvar(transacao1);
        transacaoRepository.salvar(transacao2);
        transacaoRepository.salvar(transacao3);

        mockMvc.perform(
                        get("/estatistica")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.sum").value(50))
                .andExpect(jsonPath("$.min").value(20))
                .andExpect(jsonPath("$.max").value(30))
                .andExpect(jsonPath("$.avg").value(25))
                .andReturn();
    }

    @Test
    void shouldPerformDecimalEstatisticas() throws Exception {
        var now = OffsetDateTime.now();
        var transacao1 = new Transacao(BigDecimal.valueOf(10.5), now);
        var transacao2 = new Transacao(BigDecimal.valueOf(21.25), now);
        var transacao3 = new Transacao(BigDecimal.valueOf(45.75), now);
        transacaoRepository.salvar(transacao1);
        transacaoRepository.salvar(transacao2);
        transacaoRepository.salvar(transacao3);

        mockMvc.perform(
                        get("/estatistica")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.sum").value(77.5))
                .andExpect(jsonPath("$.min").value(10.5))
                .andExpect(jsonPath("$.max").value(45.75))
                .andExpect(jsonPath("$.avg").value(25.83))
                .andReturn();
    }
}
