package com.github.hexagonoframework.textparser.exception;

import org.junit.Assert;
import org.junit.Test;

public class ParserErrorTypeTest {

	@Test
	public void test() {
		Assert.assertEquals(ParserErrorType.valueOf(ParserErrorType.ERR_PARSE_RUNTIME.name()), ParserErrorType.ERR_PARSE_RUNTIME);
	}
}
