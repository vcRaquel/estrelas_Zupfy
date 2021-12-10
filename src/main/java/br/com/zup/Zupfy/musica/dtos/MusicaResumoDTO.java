package br.com.zup.Zupfy.musica.dtos;

public class MusicaResumoDTO {
    private int id;
    private String nome;
    private String detalhes;

    public MusicaResumoDTO() {
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhes() {
        return "localhos:8080/musicas/"+id;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
