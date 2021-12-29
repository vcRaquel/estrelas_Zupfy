package br.com.zup.Zupfy.musica;

import br.com.zup.Zupfy.musica.dtos.MusicaCadastroDTO;
import br.com.zup.Zupfy.musica.dtos.MusicaDetalhesDTO;
import br.com.zup.Zupfy.musica.dtos.MusicaResumoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/musicas")
public class MusicaController {

    @Autowired
    private MusicaService musicaService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MusicaDetalhesDTO cadastrarMusica(@RequestBody @Valid MusicaCadastroDTO musicaCadastroDTO){
        Musica musica = musicaService.cadastrarMusica(modelMapper.map(musicaCadastroDTO, Musica.class));
        return modelMapper.map(musica, MusicaDetalhesDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List <MusicaResumoDTO> retornarTodasAsMusicas(){
        List<MusicaResumoDTO> musicas = new ArrayList<>();

        for (Musica musica : musicaService.retornarTodasAsMusicas()){
            MusicaResumoDTO musicaResumoDTO = modelMapper.map(musica, MusicaResumoDTO.class);
            musicas.add(musicaResumoDTO);
        }
        return musicas;
    }

    @DeleteMapping(path = {("/{id}")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarMusica(@PathVariable int id){
        musicaService.deletarMusica(id);
    }

}







