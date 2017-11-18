package com.github.hexagonoframework.textparser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertNotNull;

import com.github.hexagonoframework.textparser.exception.ParserErrorType;
import com.github.hexagonoframework.textparser.exception.ParserException;
import org.junit.Test;

import com.github.hexagonoframework.textparser.exception.Error;
import com.github.hexagonoframework.textparser.registro.AtributoInvalidoRegistro;
import com.github.hexagonoframework.textparser.registro.CampoIntegerRegistro;
import com.github.hexagonoframework.textparser.registro.CampoLongRegistro;
import com.github.hexagonoframework.textparser.registro.CampoObjectRegistro;
import com.github.hexagonoframework.textparser.registro.CampoRegistro;
import com.github.hexagonoframework.textparser.registro.CampoStringRegistro;
import com.github.hexagonoframework.textparser.registro.ConstrutorInvalidoRegistro;
import com.github.hexagonoframework.textparser.registro.TesteRegistro;

public class ParserTest {

    private Class<?> clazz;
    private Throwable throwable;

    @Test
    public void deveFalharQuandoClasseNull() {
        // given
        clazz = null;

        // when
        throwable = catchThrowable(() -> RegistroParser.toRegistro("A", clazz));

        // then
        assertSameError(throwable, Error.of(ParserErrorType.ERR_PARSE_CLASSE_NULA));
    }
    
    @Test
    public void deveFalharQuandoDadosNull() {
        // given
        clazz = TesteRegistro.class;

        // when
        throwable = catchThrowable(() -> RegistroParser.toRegistro(null, clazz));

        // then
        assertSameError(throwable, Error.of(ParserErrorType.ERR_PARSE_DADOS_VAZIO));
    }

    @Test
    public void deveFalharQuandoDadosVazio() {
        // given
        clazz = TesteRegistro.class;

        // when
        throwable = catchThrowable(() -> RegistroParser.toRegistro("", clazz));

        // then
        assertSameError(throwable, Error.of(ParserErrorType.ERR_PARSE_DADOS_VAZIO));
    }

    @Test
    public void deveFalharQuandoTamanhoDadosDiferenteRegistro() {
        // given
        String dados = "XX";
        clazz = TesteRegistro.class;

        // when
        throwable = catchThrowable(() -> RegistroParser.toRegistro(dados, clazz));

        // then
        assertSameError(throwable,
                Error.of(ParserErrorType.ERR_PARSE_TAMANHO_DADOS_DIFERENTE_REGISTRO, dados.length(), TesteRegistro.CAMPO.length()));
    }

    @Test
    public void deveConverterCampoString() {
        // given
        String dados = "A";

        // when
        CampoStringRegistro registro = RegistroParser.toRegistro(dados,  CampoStringRegistro.class);

        // then
        assertNotNull(registro);
        assertThat(registro.campo).isEqualTo(dados);
    }

    @Test
    public void deveConverterCampoInteger() {
        // given
        String dados = "12";

        // when
        CampoIntegerRegistro registro = RegistroParser.toRegistro(dados,  CampoIntegerRegistro.class);

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
        CampoLongRegistro registro = RegistroParser.toRegistro(dados,  CampoLongRegistro.class);

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
        CampoRegistro registro = RegistroParser.toRegistro(dados,  CampoRegistro.class);

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
        throwable = catchThrowable(() -> RegistroParser.toRegistro("A", clazz));

        // then
        assertSameError(throwable, Error.of(ParserErrorType.ERR_PARSE_CONVERTER_NAO_ENCONTRADO, Object.class.getName()));
    }
    
    @Test
    public void deveFalharQuandoConstrutorInvalido() {
    	// given
    	clazz = ConstrutorInvalidoRegistro.class;
    	
    	// when
        throwable = catchThrowable(() -> RegistroParser.toRegistro("A", clazz));

        // then
        assertSameError(throwable, Error.of(ParserErrorType.ERR_PARSE_CLASSE_CONSTRUTOR_INVALIDO, clazz.getName()));
    }
    
    @Test
    public void deveFalharQuandoAtributoInvalido() {
        // given
        clazz = AtributoInvalidoRegistro.class;

        // when
        throwable = catchThrowable(() -> RegistroParser.toRegistro("A", clazz));

        // then
        assertSameError(throwable, Error.of(ParserErrorType.ERR_PARSE_CLASSE_ATRIBUTO_INVALIDO, "campo", clazz.getName()));
    }

    private void assertSameError(Throwable throwable, Error error) {
        assertThat(throwable).isInstanceOf(ParserException.class);
        assertThat(((ParserException) throwable).getError()).isEqualTo(error);
    }
}
