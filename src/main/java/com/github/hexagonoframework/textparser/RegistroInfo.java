package com.github.hexagonoframework.textparser;

import java.util.Map;
import java.util.Map.Entry;

class RegistroInfo {

    private Class<?> registroClass;
    private Map<Integer, CampoInfo> campos;
    private int tamanhoTotal = 0;

    public RegistroInfo(Class<?> registroClass, Map<Integer, CampoInfo> campos) {
        this.registroClass = registroClass;
        this.campos = campos;
        atribuiTamanhoTotal();
    }

    private void atribuiTamanhoTotal() {
        for (Entry<Integer, CampoInfo> entry : campos.entrySet()) {
            tamanhoTotal += entry.getValue().getTamanho();
        }
    }

    public Class<?> getRegistroClass() {
        return registroClass;
    }

    public Map<Integer, CampoInfo> getCampos() {
        return campos;
    }

    public int getTamanhoTotal() {
        return tamanhoTotal;
    }
}
