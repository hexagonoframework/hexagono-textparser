package com.github.hexagonoframework.textparser;

import static com.github.hexagonoframework.core.exception.Error.of;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_CLASSE_NAO_RECORD;
import static com.github.hexagonoframework.textparser.exception.TextParserErrorType.PARSE_REGISTRO_NULO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.hexagonoframework.core.exception.Error;
import com.github.hexagonoframework.textparser.exception.TextParserException;
import com.github.hexagonoframework.textparser.registro.CampoIntegerRegistro;
import com.github.hexagonoframework.textparser.registro.CampoStringRegistro;

public class ObjectParserTest {

    private Throwable throwable;

    @Test
    public void deveFalharQuandoRegistroNulo() {
        throwable = catchThrowable(() -> TextParser.toText(null));
        assertSameError(throwable, of(PARSE_REGISTRO_NULO));
    }

    @Test
    public void deveFalharQuandoNaoForRegistro() {
        // given
        Integer registro = 1;

        // when
        throwable = catchThrowable(() -> TextParser.toText(registro));

        // then
        assertSameError(throwable, of(PARSE_CLASSE_NAO_RECORD, Integer.class.getName()));
    }

    @Test
    public void deveConverterCampoString() {
        // given
        CampoStringRegistro registro = new CampoStringRegistro();
        registro.campo = "X";

        // when
        String dados = TextParser.toText(registro);

        // then
        assertEquals(registro.campo, dados);
    }

    @Test
    public void deveConverterCampoInteger() {
        // given
        CampoIntegerRegistro registro = new CampoIntegerRegistro();
        registro.campo1 = 1;
        registro.campo2 = 2;

        // when
        String dados = TextParser.toText(registro);

        // then
        assertEquals(dados, "12");
    }

    private void assertSameError(Throwable throwable, Error error) {
        assertThat(throwable).isInstanceOf(TextParserException.class);
        assertThat(((TextParserException) throwable).getError()).isEqualTo(error);
    }
}
