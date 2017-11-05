package org.hexagonoframework.textparser.registro;

import org.hexagonoframework.textparser.annotation.Campo;
import org.hexagonoframework.textparser.annotation.Registro;

@Registro
public class CampoStringRegistro {

    @Campo(posicao = 1, tamanho = 1)
    public String campo;
}
