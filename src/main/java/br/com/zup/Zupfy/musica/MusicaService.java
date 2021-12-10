package br.com.zup.Zupfy.musica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MusicaService {
    @Autowired
    private MusicaRepository musicaRepository;

    public Musica cadastrarMusica(Musica musica){
        return musica;
    }

    public void deletarMusica(int id){

    }

    public Musica atualizarMusica(Musica musica){
        return musica;
    }

    public List<Musica> retornarTodasAsMusicas(){
        return null;
    }
}
