package br.com.zup.Zupfy.musica;

import br.com.zup.Zupfy.Componentes.Conversor;
import br.com.zup.Zupfy.enuns.Estilo;
import br.com.zup.Zupfy.musica.dtos.MusicaCadastroDTO;
import br.com.zup.Zupfy.musica.dtos.MusicaDetalhesDTO;
import br.com.zup.Zupfy.musica.dtos.MusicaResumoDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest({MusicaController.class, Conversor.class})
public class MusicaControllerTest {
    @MockBean
    private MusicaService musicaService;


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Musica musica;
    private MusicaCadastroDTO musicaCadastroDTO;

    @BeforeEach
    public void setup(){
        musica = new Musica();
        musica.setNome("Samba de um Janota Só");

        musicaCadastroDTO = new MusicaCadastroDTO();
        musicaCadastroDTO.setEstilo(Estilo.ROCK);
        musicaCadastroDTO.setNome("Hard Sun");

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testarCadastroDeMusica() throws Exception {
        Mockito.when(musicaService.cadastrarMusica(Mockito.any(Musica.class))).thenReturn(musica);
        String json = objectMapper.writeValueAsString(musicaCadastroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/musicas")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        var musicaResposta = objectMapper.readValue(jsonResposta, MusicaDetalhesDTO.class);

    }

    @Test
    public void testarCadastroDeMusicaValidacaoNome() throws Exception{
        musicaCadastroDTO.setNome("");
        Mockito.when(musicaService.cadastrarMusica(Mockito.any(Musica.class))).thenReturn(musica);
        String json = objectMapper.writeValueAsString(musicaCadastroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/musicas")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    public void testarCadastroDeMusicaValidacaoEstilo() throws Exception{
        Mockito.when(musicaService.cadastrarMusica(Mockito.any(Musica.class))).thenReturn(musica);
        String json = objectMapper.writeValueAsString(musicaCadastroDTO);
        json = json.replace("\"estilo\":\"ROCK\"}", "\"estilo\":\"Teste\"}");

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/musicas")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    public void testarVisualizacaoDeMusicas() throws Exception{
        Mockito.when(musicaService.retornarTodasAsMusicas()).thenReturn(Arrays.asList(musica));

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/musicas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        List<MusicaResumoDTO> musicas = objectMapper.readValue(jsonResposta, new TypeReference<List<MusicaResumoDTO>>()
        {});
    }

    @Test
    public void testarDeletarMuscia() throws Exception {
        musica.setId(1);
        Mockito.doNothing().when(musicaService).deletarMusica(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/musicas/"+musica.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));

        Mockito.verify(musicaService, Mockito.times(1)).deletarMusica(Mockito.anyInt());
    }

    @Test
    public void testarDeletarMusciaNaoExiste() throws Exception {
        Mockito.doNothing().when(musicaService).deletarMusica(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/musicas/"+musica.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

        Mockito.verify(musicaService, Mockito.times(0)).deletarMusica(Mockito.anyInt());
    }

}
