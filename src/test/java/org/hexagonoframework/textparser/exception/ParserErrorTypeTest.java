package org.hexagonoframework.textparser.exception;

import static org.hexagonoframework.textparser.exception.ParserErrorType.ERR_PARSE_RUNTIME;
import static org.junit.Assert.*;

import org.junit.Test;

public class ParserErrorTypeTest {

	@Test
	public void test() {
		assertEquals(ParserErrorType.valueOf(ERR_PARSE_RUNTIME.name()), ERR_PARSE_RUNTIME);
	}
}
