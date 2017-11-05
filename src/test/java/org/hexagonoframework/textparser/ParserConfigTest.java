package org.hexagonoframework.textparser;

import org.hexagonoframework.textparser.annotation.Campo;
import org.hexagonoframework.textparser.annotation.Registro;
import org.hexagonoframework.textparser.exception.Error;
import org.hexagonoframework.textparser.exception.ParserException;
import org.junit.Test;

import static org.hexagonoframework.textparser.exception.ParserErrorType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ParserConfigTest {

    private Class clazz;
    private Throwable throwable;

    @Test
    public void deveFalharQuandoClasseNula() {
        // given
        clazz = null;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CLASSE_NULA));
    }

    @Test
    public void deveFalharQuandoClasseNaoForRegistro() throws Exception {
        // given
        String className = "java.lang.Object";
        clazz = Class.forName(className);

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CLASSE_NAO_RECORD, className));
    }

    @Test
    public void deveFalharQuandoRegistroNaoTiverCampo() {
        // given
        @Registro class SemCampoRegistro { Object object; }
        clazz = SemCampoRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_REGISTRO_SEM_CAMPO));
    }

    @Test
    public void deveFalharQuandoPosicaoZerada() {
        // given
        @Registro class PosicaoZeradaRegistro {
            @Campo(posicao = 0, tamanho = 1) Object campo1;
        }
        clazz = PosicaoZeradaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CAMPO_POSICAO_INVALIDO, 0, "campo1", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoPosicaoNegativa() {
        // given
        @Registro class PosicaoNegativaRegistro {
            @Campo(posicao = -1, tamanho = 1) Object campo1;
            @Campo(posicao = 1, tamanho = 1) Object campo2;
        }
        clazz = PosicaoNegativaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CAMPO_POSICAO_INVALIDO, -1, "campo1", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoPosicoesDuplicadas() {
        // given
        @Registro class PosicaoDuplicadaRegistro {
            @Campo(posicao = 1, tamanho = 1) Object campo1;
            @Campo(posicao = 1, tamanho = 1) Object campo2;
        }
        clazz = PosicaoDuplicadaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CAMPOS_POSICOES_DUPLICADAS, 1, "campo1", "campo2", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoPosicaoInicialDiferenteDe1() {
        // given
        @Registro class PosicaoInicialInvalidaRegistro {
            @Campo(posicao = 2, tamanho = 1) Object field;
        }
        clazz = PosicaoInicialInvalidaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_POSICAO_INICIAL_INVALIDA, clazz.getName()));
    }

    @Test
    public void deveFalharQuandoSequenciaDePosicoesInvalidas() {
        // given
        @Registro class SequenciaPosicoesInvalidaRegistro {
            @Campo(posicao = 1, tamanho = 1) Object campo1;
            @Campo(posicao = 2, tamanho = 1) Object campo2;
            @Campo(posicao = 4, tamanho = 1) Object campo4;
        }
        clazz = SequenciaPosicoesInvalidaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CAMPOS_POSICOES_INVALIDAS, clazz.getName(), "[1, 2, 4]"));
    }

    @Test
    public void deveFalharQuandoTamanhoNegativo() {
        // given
        @Registro class TamanhoNegativoRegistro {
            @Campo(posicao = 1, tamanho = -1) Object campo;
        }
        clazz = TamanhoNegativoRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CAMPO_TAMANHO_INVALIDO, -1, "campo", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoTamanhoZerado() {
        // given
        @Registro class TamanhoZeradoRegistro {
            @Campo(posicao = 1, tamanho = 0) Object campo;
        }
        clazz = TamanhoZeradoRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, Error.of(ERR_PARSE_CAMPO_TAMANHO_INVALIDO, 0, "campo", clazz.getName()));
    }

    private void assertSameError(Throwable throwable, Error error) {
        assertThat(throwable).isInstanceOf(ParserException.class);
        assertThat(((ParserException) throwable).getError()).isEqualTo(error);
    }
}
