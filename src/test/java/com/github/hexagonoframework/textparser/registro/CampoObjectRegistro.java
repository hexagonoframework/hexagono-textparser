package com.github.hexagonoframework.textparser.registro;

import com.github.hexagonoframework.textparser.annotation.Campo;
import com.github.hexagonoframework.textparser.annotation.Registro;

@Registro
public class CampoObjectRegistro {

    @Campo(posicao = 1, tamanho = 1)
    public Object campo;
}
