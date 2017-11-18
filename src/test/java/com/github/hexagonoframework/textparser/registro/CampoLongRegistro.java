package com.github.hexagonoframework.textparser.registro;

import com.github.hexagonoframework.textparser.annotation.Campo;
import com.github.hexagonoframework.textparser.annotation.Registro;

@Registro
public class CampoLongRegistro {

    @Campo(posicao = 1, tamanho = 1)
    public long campo1;

    @Campo(posicao = 2, tamanho = 1)
    public Long campo2;
}