package br.com.zup.Zupfy.musica.dtos;

import br.com.zup.Zupfy.enuns.Estilo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MusicaCadastroDTO {
    @Size(min = 1)
    @NotNull
    private String nome;
    @NotNull
    private Estilo estilo;

    public MusicaCadastroDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }
}
