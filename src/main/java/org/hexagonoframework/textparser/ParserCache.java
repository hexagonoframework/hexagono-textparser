package org.hexagonoframework.textparser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ParserCache {

	private static final Map<Class<?>, RegistroInfo> cache = new ConcurrentHashMap<>();

	static {
		new ParserCache();
	}
	
	private ParserCache() {}
	
	static void put(Class<?> key, RegistroInfo value) {
		cache.putIfAbsent(key, value);
	}

	static RegistroInfo get(Class<?> key) {
		return cache.get(key);
	}
	
	static boolean containsKey(Class<?> key) {
		return cache.containsKey(key);
	}
	
	/**
	 * Exclusivo para uso em testes unitários
	 */
	static void clear() {
		cache.clear(); 
	}
}
