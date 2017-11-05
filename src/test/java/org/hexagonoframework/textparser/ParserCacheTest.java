package org.hexagonoframework.textparser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.hexagonoframework.textparser.registro.TesteRegistro;

public class ParserCacheTest {

	@Before
	public void before() {
		ParserCache.clear();
	}

	@Test
	public void inclusaoNoCache() {
		// dado que não existe no cache
		Class<TesteRegistro> clazz = TesteRegistro.class;
		assertFalse(ParserCache.containsKey(clazz));

		// quando incluo no cache
		ParserConfig config = new ParserConfig(clazz);
		RegistroInfo original = new RegistroInfo(clazz, config.getCampos());
		ParserCache.put(clazz, original);

		// então deve estar presente no cache
		assertTrue(ParserCache.containsKey(clazz));
	}

	@Test
	public void recuperacaoDoCache() {
		// dado que incluo no cache
		Class<TesteRegistro> clazz = TesteRegistro.class;
		ParserConfig config = new ParserConfig(clazz);
		RegistroInfo original = new RegistroInfo(clazz, config.getCampos());
		ParserCache.put(clazz, original);

		// quando obtenho do cache
		RegistroInfo cache = ParserCache.get(clazz);

		// então deve ser igual
		assertEquals(cache, original);
	}
}
