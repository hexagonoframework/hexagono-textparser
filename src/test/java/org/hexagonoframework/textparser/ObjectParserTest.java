package org.hexagonoframework.textparser;

import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_CLASSE_NAO_RECORD;
import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_REGISTRO_NULO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.hexagonoframework.textparser.exception.Error;
import org.hexagonoframework.textparser.exception.ParserException;
import org.hexagonoframework.textparser.registro.CampoIntegerRegistro;
import org.hexagonoframework.textparser.registro.CampoStringRegistro;

public class ObjectParserTest {

	private Throwable throwable;
	
	@Test
	public void deveFalharQuandoRegistroNulo() {
		throwable = catchThrowable(() -> RegistroParser.fromRegistro(null));
		assertSameError(throwable, Error.of(ERR_PARSE_REGISTRO_NULO));
	}
	
	@Test
	public void deveFalharQuandoNaoForRegistro() {
		// given
		Integer registro = 1;
		
		// when	
		throwable = catchThrowable(() -> RegistroParser.fromRegistro(registro));
		
		// then
		assertSameError(throwable, Error.of(ERR_PARSE_CLASSE_NAO_RECORD, Integer.class.getName()));
	}
	
	@Test
	public void deveConverterCampoString() {
		// given
		CampoStringRegistro registro = new CampoStringRegistro();
		registro.campo = "X";
		
		// when
		String dados = RegistroParser.fromRegistro(registro);
		
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
		String dados = RegistroParser.fromRegistro(registro);
		
		// then
		assertEquals(dados, "12");
	}
	
    private void assertSameError(Throwable throwable, Error error) {
        assertThat(throwable).isInstanceOf(ParserException.class);
        assertThat(((ParserException) throwable).getError()).isEqualTo(error);
    }
}
