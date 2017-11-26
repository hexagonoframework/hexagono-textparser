package com.github.hexagonoframework.textparser;

import static com.github.hexagonoframework.core.exception.Error.of;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_ATRIBUTO_INVALIDO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_CONSTRUTOR_INVALIDO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_NULA;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CONVERTER_NAO_ENCONTRADO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_DADOS_VAZIO;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.hexagonoframework.core.exception.Error;
import com.github.hexagonoframework.textparser.exception.TextParserException;
import com.github.hexagonoframework.textparser.registro.AtributoInvalidoRegistro;
import com.github.hexagonoframework.textparser.registro.CampoIntegerRegistro;
import com.github.hexagonoframework.textparser.registro.CampoLongRegistro;
import com.github.hexagonoframework.textparser.registro.CampoObjectRegistro;
import com.github.hexagonoframework.textparser.registro.CampoRegistro;
import com.github.hexagonoframework.textparser.registro.CampoStringRegistro;
import com.github.hexagonoframework.textparser.registro.ConstrutorInvalidoRegistro;
import com.github.hexagonoframework.textparser.registro.TesteRegistro;

public class ParseFromTextTest {

    private Class<?> clazz;
    private Throwable throwable;

    @Test
    public void deveFalharQuandoClasseNull() {
        // given
        clazz = null;

        // when
        throwable = catchThrowable(() -> TextParser.fromText("A", clazz));

        // then
        assertSameError(throwable, of(PARSE_CLASSE_NULA));
    }

    @Test
    public void deveFalharQuandoDadosNull() {
        // given
        clazz = TesteRegistro.class;

        // when
        throwable = catchThrowable(() -> TextParser.fromText(null, clazz));

        // then
        assertSameError(throwable, of(PARSE_DADOS_VAZIO));
    }

    @Test
    public void deveFalharQuandoDadosVazio() {
        // given
        clazz = TesteRegistro.class;

        // when
        throwable = catchThrowable(() -> TextParser.fromText("", clazz));

        // then
        assertSameError(throwable, of(PARSE_DADOS_VAZIO));
    }

    @Test
    public void deveFalharQuandoTamanhoDadosDiferenteRegistro() {
        // given
        String dados = "XX";
        clazz = TesteRegistro.class;

        // when
        throwable = catchThrowable(() -> TextParser.fromText(dados, clazz));

        // then
        assertSameError(throwable,
                of(PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO, dados.length(), TesteRegistro.CAMPO.length()));
    }

    @Test
    public void deveConverterCampoString() {
        // given
        String dados = "A";

        // when
        CampoStringRegistro registro = TextParser.fromText(dados, CampoStringRegistro.class);

        // then
        assertNotNull(registro);
        assertThat(registro.campo).isEqualTo(dados);
    }

    @Test
    public void deveConverterCampoInteger() {
        // given
        String dados = "12";

        // when
        CampoIntegerRegistro registro = TextParser.fromText(dados, CampoIntegerRegistro.class);

        // then
        assertNotNull(registro);
        assertThat(registro.campo1).isEqualTo(1);
        assertThat(registro.campo2).isEqualTo(2);
    }

    @Test
    public void deveConverterCampoLong() {
        // given
        String dados = "12";

        // when
        CampoLongRegistro registro = TextParser.fromText(dados, CampoLongRegistro.class);

        // then
        assertNotNull(registro);
        assertThat(registro.campo1).isEqualTo(1);
        assertThat(registro.campo2).isEqualTo(2);
    }

    @Test
    public void deveConverterCampoRegistro() {
        // given
        String dados = "AB";

        // when
        CampoRegistro registro = TextParser.fromText(dados, CampoRegistro.class);

        // then
        assertNotNull(registro);
        assertThat(registro.campoRegistro.campo).isEqualTo("A");
        assertThat(registro.campo).isEqualTo("B");
    }

    @Test
    public void deveFalharQuandoConversaoNaoSuportada() {
        // given
        clazz = CampoObjectRegistro.class;

        // when
        throwable = catchThrowable(() -> TextParser.fromText("A", clazz));

        // then
        assertSameError(throwable, of(PARSE_CONVERTER_NAO_ENCONTRADO, Object.class.getName()));
    }

    @Test
    public void deveFalharQuandoConstrutorInvalido() {
        // given
        clazz = ConstrutorInvalidoRegistro.class;

        // when
        throwable = catchThrowable(() -> TextParser.fromText("A", clazz));

        // then
        assertSameError(throwable, of(PARSE_CLASSE_CONSTRUTOR_INVALIDO, clazz.getName()));
    }

    @Test
    public void deveFalharQuandoAtributoInvalido() {
        // given
        clazz = AtributoInvalidoRegistro.class;

        // when
        throwable = catchThrowable(() -> TextParser.fromText("A", clazz));

        // then
        assertSameError(throwable, of(PARSE_CLASSE_ATRIBUTO_INVALIDO, "campo", clazz.getName()));
    }

    private void assertSameError(Throwable throwable, Error error) {
        assertThat(throwable).isInstanceOf(TextParserException.class);
        assertThat(((TextParserException) throwable).getError()).isEqualTo(error);
    }
}
