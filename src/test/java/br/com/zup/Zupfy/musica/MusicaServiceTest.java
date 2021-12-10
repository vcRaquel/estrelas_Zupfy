package br.com.zup.Zupfy.musica;

import br.com.zup.Zupfy.enuns.Estilo;
import br.com.zup.Zupfy.musica.dtos.MusicaCadastroDTO;
import br.com.zup.Zupfy.musica.exceptions.MusicaNaoEcontradaExeception;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

@SpringBootTest
public class MusicaServiceTest {
    @MockBean
    private MusicaRepository musicaRepository;
    @Autowired
    private MusicaService musicaService;

    private Musica musica;

    @BeforeEach
    public void setup(){
        musica = new Musica();
        musica.setNome("Samba de um Janota Só");

    }

    @Test
    public void testarCadastroDeMusica(){
        Mockito.when(musicaRepository.save(Mockito.any(Musica.class)))
                .thenAnswer(objto -> objto.getArgument(0, Musica.class));

        Musica musicaSalva = musicaService.cadastrarMusica(musica);

        Assertions.assertEquals(LocalDate.now(), musicaSalva.getDataDeCadastro());
    }

    @Test
    public void testarDeletarMusicaNaoEncontrada(){
        Mockito.doNothing().when(musicaRepository).delete(Mockito.any(Musica.class));

        MusicaNaoEcontradaExeception exeception = Assertions.assertThrows(MusicaNaoEcontradaExeception.class, () -> {
            musicaService.deletarMusica(8001);
        });
        Assertions.assertEquals("Musica não encontrada", exeception.getMessage());
    }
}
