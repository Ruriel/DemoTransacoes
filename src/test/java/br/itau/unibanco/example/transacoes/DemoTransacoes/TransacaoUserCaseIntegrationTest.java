package br.itau.unibanco.example.transacoes.DemoTransacoes;

import br.itau.unibanco.example.transacoes.DemoTransacoes.application.dto.input.TransacaoInputDto;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.model.Transacao;
import br.itau.unibanco.example.transacoes.DemoTransacoes.domain.repository.TransacaoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoTransacoesApplication.class)
@AutoConfigureMockMvc
public class TransacaoUserCaseIntegrationTest {
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

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ⬅️ IMPORTANTE

            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void shouldSaveTransacao() throws Exception {
        var now = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS);;
        var transacao1 = new TransacaoInputDto("10,00", now);
        var content =  asJsonString(transacao1);
        mockMvc.perform(
                        post("/transacao")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.valor").value("10.00"))
                .andExpect(jsonPath("$.dataHora").value(now.toString()))
                .andReturn();

    }
}
