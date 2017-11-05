package org.hexagonoframework.textparser.registro;

import org.hexagonoframework.textparser.annotation.Campo;
import org.hexagonoframework.textparser.annotation.Registro;

@Registro
public class AtributoInvalidoRegistro {

    @Campo(posicao = 1, tamanho = 1)
    private String campo;
}
