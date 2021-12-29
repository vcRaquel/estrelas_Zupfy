package br.com.zup.Zupfy.Config;

import br.com.zup.Zupfy.musica.exceptions.MusicaNaoEcontradaExeception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<MensagemDeErro> tratarMethodArgumentNotValidException(MethodArgumentNotValidException excecao) {
        List<MensagemDeErro> erros = new ArrayList<>();

        for (FieldError referencia : excecao.getFieldErrors()) {
            MensagemDeErro mensagem = new MensagemDeErro(referencia.getDefaultMessage());
            erros.add(mensagem);
        }

        return erros;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity enumInvalidoException(HttpMessageNotReadableException exception) {
        if (exception.getLocalizedMessage().contains("br.com.zup.Zupfy.enuns.Estilo")) {
            return ResponseEntity.status(422).build();
        }
        return ResponseEntity.status(400).build();
    }

    @ExceptionHandler(MusicaNaoEcontradaExeception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MensagemDeErro tratarMusicaNaoEcontradaExeception(
            MusicaNaoEcontradaExeception exception) {
        return new MensagemDeErro(exception.getMessage());
    }

}
