package com.github.hexagonoframework.textparser.exception;

import com.github.hexagonoframework.textparser.annotation.Campo;
import com.github.hexagonoframework.core.exception.ErrorType;

public enum TextParserErrorType implements ErrorType {

    RUNTIME_ERROR("Erro de runtime inesperado")

    , PARSE_CLASSE_NULA("Classe nula")

    , PARSE_CLASSE_NAO_RECORD("Classe {0} não é um Registro. Anotar com @Registro")
    
    , PARSE_CLASSE_CONSTRUTOR_INVALIDO("Não foi possível criar uma nova instância da classe {0}")
    
    , PARSE_CLASSE_ATRIBUTO_INVALIDO("Não foi possível atribuir valor para campo {0} da classe {1}")
    
    , PARSE_REGISTRO_NULO("Registro nulo")
    
    , PARSE_REGISTRO_CAMPO_VALOR("Erro a realizar parse do valor para Campo {0} do Registro {1}")

    , PARSE_DADOS_VAZIO("Dados nulo ou vazio")

    , PARSE_REGISTRO_SEM_CAMPO("Registro {0} não possui campos anotados com @" + Campo.class.getName())

    , PARSE_CAMPO_POSICAO_INVALIDO("Posição {0} inválida para Campo {1} do Registro {2}")

    , PARSE_CAMPOS_POSICOES_DUPLICADAS("Posicao {0} duplicada para campos {1} e {2} do Registro {3}")

    , PARSE_POSICAO_INICIAL_INVALIDA("Registro {0} não possui posicao inicial igual a 1")

    , PARSE_CAMPOS_POSICOES_INVALIDAS("Registro {0} possui campos com posições fora de sequencia: {1}")

    , PARSE_CAMPO_TAMANHO_INVALIDO("Tamanho {0} inválido para Campo {1} do Registro {2}")

    , PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO("Tamanho da string {0} diferente de total do registro {1}")
    
    , PARSE_CONVERTER_NAO_ENCONTRADO("Não foi possível identificar o converter correto para a classe {0}")
    
    ;

    private String pattern;

    TextParserErrorType(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

}
