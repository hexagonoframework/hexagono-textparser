package com.github.hexagonoframework.textparser.registro;

import com.github.hexagonoframework.textparser.annotation.Campo;
import com.github.hexagonoframework.textparser.annotation.Registro;

@Registro
public class AtributoInvalidoRegistro {

    @Campo(posicao = 1, tamanho = 1)
    private String campo;
}
