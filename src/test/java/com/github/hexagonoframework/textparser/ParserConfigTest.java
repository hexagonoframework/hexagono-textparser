package com.github.hexagonoframework.textparser;

import static com.github.hexagonoframework.core.exception.Error.of;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CAMPOS_POSICOES_DUPLICADAS;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CAMPOS_POSICOES_INVALIDAS;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CAMPO_POSICAO_INVALIDO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CAMPO_TAMANHO_INVALIDO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_NAO_RECORD;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_NULA;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_POSICAO_INICIAL_INVALIDA;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_REGISTRO_SEM_CAMPO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.github.hexagonoframework.core.exception.Error;
import com.github.hexagonoframework.textparser.annotation.Campo;
import com.github.hexagonoframework.textparser.annotation.Registro;
import com.github.hexagonoframework.textparser.exception.TextParserException;

public class ParserConfigTest {

    private Class<?> clazz;
    private Throwable throwable;

    @Test
    public void deveFalharQuandoClasseNula() {
        // given
        clazz = null;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CLASSE_NULA));
    }

    @Test
    public void deveFalharQuandoClasseNaoForRegistro() throws Exception {
        // given
        String className = "java.lang.Object";
        clazz = Class.forName(className);

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CLASSE_NAO_RECORD, className));
    }

    @Test
    public void deveFalharQuandoRegistroNaoTiverCampo() {
        // given
        @Registro
        class SemCampoRegistro {}
        clazz = SemCampoRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_REGISTRO_SEM_CAMPO));
    }

    @Test
    public void deveFalharQuandoPosicaoZerada() {
        // given
        @Registro
        class PosicaoZeradaRegistro {
            @Campo(posicao = 0, tamanho = 1)
            Object campo1;
        }
        clazz = PosicaoZeradaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CAMPO_POSICAO_INVALIDO, 0, "campo1", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoPosicaoNegativa() {
        // given
        @Registro
        class PosicaoNegativaRegistro {
            @Campo(posicao = -1, tamanho = 1)
            Object campo1;
            @Campo(posicao = 1, tamanho = 1)
            Object campo2;
        }
        clazz = PosicaoNegativaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CAMPO_POSICAO_INVALIDO, -1, "campo1", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoPosicoesDuplicadas() {
        // given
        @Registro
        class PosicaoDuplicadaRegistro {
            @Campo(posicao = 1, tamanho = 1)
            Object campo1;
            @Campo(posicao = 1, tamanho = 1)
            Object campo2;
        }
        clazz = PosicaoDuplicadaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CAMPOS_POSICOES_DUPLICADAS, 1, "campo1", "campo2", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoPosicaoInicialDiferenteDe1() {
        // given
        @Registro
        class PosicaoInicialInvalidaRegistro {
            @Campo(posicao = 2, tamanho = 1)
            Object field;
        }
        clazz = PosicaoInicialInvalidaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_POSICAO_INICIAL_INVALIDA, clazz.getName()));
    }

    @Test
    public void deveFalharQuandoSequenciaDePosicoesInvalidas() {
        // given
        @Registro
        class SequenciaPosicoesInvalidaRegistro {
            @Campo(posicao = 1, tamanho = 1)
            Object campo1;
            @Campo(posicao = 2, tamanho = 1)
            Object campo2;
            @Campo(posicao = 4, tamanho = 1)
            Object campo4;
        }
        clazz = SequenciaPosicoesInvalidaRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CAMPOS_POSICOES_INVALIDAS, clazz.getName(), "[1, 2, 4]"));
    }

    @Test
    public void deveFalharQuandoTamanhoNegativo() {
        // given
        @Registro
        class TamanhoNegativoRegistro {
            @Campo(posicao = 1, tamanho = -1)
            Object campo;
        }
        clazz = TamanhoNegativoRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CAMPO_TAMANHO_INVALIDO, -1, "campo", clazz.getName()));
    }

    @Test
    public void deveFalharQuandoTamanhoZerado() {
        // given
        @Registro
        class TamanhoZeradoRegistro {
            @Campo(posicao = 1, tamanho = 0)
            Object campo;
        }
        clazz = TamanhoZeradoRegistro.class;

        // when
        throwable = catchThrowable(() -> new ParserConfig(clazz));

        // then
        assertSameError(throwable, of(PARSE_CAMPO_TAMANHO_INVALIDO, 0, "campo", clazz.getName()));
    }

    private void assertSameError(Throwable throwable, Error error) {
        assertThat(throwable).isInstanceOf(TextParserException.class);
        Assertions.assertThat(((TextParserException) throwable).getError()).isEqualTo(error);
    }
}
