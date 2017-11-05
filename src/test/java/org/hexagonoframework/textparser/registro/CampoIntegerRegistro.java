package org.hexagonoframework.textparser.registro;

import org.hexagonoframework.textparser.annotation.Campo;
import org.hexagonoframework.textparser.annotation.Registro;

@Registro
public class CampoIntegerRegistro {

    @Campo(posicao = 1, tamanho = 1)
    public int campo1;

    @Campo(posicao = 2, tamanho = 1)
    public Integer campo2;
}