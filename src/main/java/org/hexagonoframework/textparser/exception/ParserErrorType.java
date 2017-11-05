package org.hexagonoframework.textparser.exception;

import org.hexagonoframework.textparser.annotation.Campo;

public enum ParserErrorType implements ErrorType {

    ERR_PARSE_RUNTIME("Erro de runtime inesperado")

    , ERR_PARSE_CLASSE_NULA("Classe nula")

    , ERR_PARSE_CLASSE_NAO_RECORD("Classe {0} não é um Registro. Anotar com @Registro")
    
    , ERR_PARSE_CLASSE_CONSTRUTOR_INVALIDO("Não foi possível criar uma nova instância da classe {0}")
    
    , ERR_PARSE_CLASSE_ATRIBUTO_INVALIDO("Não foi possível atribuir valor para campo {0} da classe {1}")
    
    , ERR_PARSE_REGISTRO_NULO("Registro nulo")
    
    , ERR_PARSE_REGISTRO_CAMPO_VALOR("Erro a realizar parse do valor para Campo {0} do Registro {1}")

    , ERR_PARSE_DADOS_VAZIO("Dados nulo ou vazio")

    , ERR_PARSE_REGISTRO_SEM_CAMPO("Registro {0} não possui campos anotados com @" + Campo.class.getName())

    , ERR_PARSE_CAMPO_POSICAO_INVALIDO("Posição {0} inválida para Campo {1} do Registro {2}")

    , ERR_PARSE_CAMPOS_POSICOES_DUPLICADAS("Posicao {0} duplicada para campos {1} e {2} do Registro {3}")

    , ERR_PARSE_POSICAO_INICIAL_INVALIDA("Registro {0} não possui posicao inicial igual a 1")

    , ERR_PARSE_CAMPOS_POSICOES_INVALIDAS("Registro {0} possui campos com posições fora de sequencia: {1}")

    , ERR_PARSE_CAMPO_TAMANHO_INVALIDO("Tamanho {0} inválido para Campo {1} do Registro {2}")

    , ERR_PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO("Tamanho da string {0} diferente de total do registro {1}")
    
    , ERR_PARSE_CONVERTER_NAO_ENCONTRADO("Não foi possível identificar o converter correto para a classe {0}")
    
    ;

    private String message;

    ParserErrorType(String message) {
        this.message = message;
    }

    @Override
    public String getPattern() {
        return message;
    }

}
