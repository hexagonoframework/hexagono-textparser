package com.github.hexagonoframework.textparser;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.github.hexagonoframework.textparser.annotation.Campo;
import com.github.hexagonoframework.textparser.annotation.Registro;
import com.github.hexagonoframework.textparser.exception.Error;
import com.github.hexagonoframework.textparser.exception.ParserErrorType;
import com.github.hexagonoframework.textparser.exception.ParserException;

class ParserConfig {

    private final Class<?> registroClass;
    private Map<Integer, CampoInfo> campos;

    ParserConfig(Class<?> registroClass) {
        this.registroClass = registroClass;
        assertRegistroClassIsNotNull();
        assertClassIsRegistro();

        montaCampos();

        assertRecordClassHasField();
        assertPosicaoInicial();
        assertPosicoesEmSequencia();
    }

    Map<Integer, CampoInfo> getCampos() {
        return campos;
    }

    private void assertRegistroClassIsNotNull() {
        if (null == registroClass) throw new ParserException(Error.of(ParserErrorType.ERR_PARSE_CLASSE_NULA));
    }

    private void assertClassIsRegistro() {
        if (!registroClass.isAnnotationPresent(Registro.class)) {
            throw new ParserException(Error.of(ParserErrorType.ERR_PARSE_CLASSE_NAO_RECORD, registroClass.getName()));
        }
    }

    private void montaCampos() {
        campos = new TreeMap<>();
        int indiceInicial = 0;
        for (Field field : registroClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Campo.class)) {
                int posicao = field.getAnnotation(Campo.class).posicao();
                if (posicao <= 0) {
                    throw new ParserException(
                            Error.of(ParserErrorType.ERR_PARSE_CAMPO_POSICAO_INVALIDO, posicao, field.getName(), registroClass.getName()));
                }

                if (campos.get(posicao) != null) {
                    throw new ParserException(Error.of(ParserErrorType.ERR_PARSE_CAMPOS_POSICOES_DUPLICADAS,
                        posicao, campos.get(posicao).getField().getName(), field.getName(), registroClass.getName()));
                }

                int tamanho = field.getAnnotation(Campo.class).tamanho();
                if (tamanho <= 0) {
                    throw new ParserException(
                            Error.of(ParserErrorType.ERR_PARSE_CAMPO_TAMANHO_INVALIDO, tamanho, field.getName(), registroClass.getName()));
                }

                campos.put(posicao, new CampoInfo(field, indiceInicial, tamanho));
                indiceInicial += tamanho;
            }
        }
    }

    private void assertRecordClassHasField() {
        if (campos.size() == 0)
            throw new ParserException(Error.of(ParserErrorType.ERR_PARSE_REGISTRO_SEM_CAMPO));
    }

    private void assertPosicaoInicial() {
        if (!campos.containsKey(1))
            throw new ParserException(Error.of(ParserErrorType.ERR_PARSE_POSICAO_INICIAL_INVALIDA, registroClass.getName()));
    }

    private void assertPosicoesEmSequencia() {
        Set<Integer> posicoes = campos.keySet();
        for (int i = 1; i <= posicoes.size(); i++) {
            if (!campos.containsKey(i)) {
                throw new ParserException(Error.of(ParserErrorType.ERR_PARSE_CAMPOS_POSICOES_INVALIDAS, registroClass.getName(), posicoes.toString()));
            }
        }
    }
}
