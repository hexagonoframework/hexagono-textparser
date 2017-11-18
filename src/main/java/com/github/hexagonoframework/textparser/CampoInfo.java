package com.github.hexagonoframework.textparser;

import java.lang.reflect.Field;

class CampoInfo {

    private final Field field;
    private final int posicaoInicial;
    private final int tamanho;
    
    CampoInfo(Field field, int posicaoInicial, int tamanho) {
        this.field = field;
        this.posicaoInicial = posicaoInicial;
        this.tamanho = tamanho;
    }

    public Field getField() {
        return field;
    }

    public int getPosicaoInicial() {
        return posicaoInicial;
    }

    public int getTamanho() {
        return tamanho;
    }
}