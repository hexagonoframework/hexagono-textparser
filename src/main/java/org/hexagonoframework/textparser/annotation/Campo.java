package org.hexagonoframework.textparser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Campo {

	int posicao();
	int tamanho();
	PadAlign padAlign() default PadAlign.DEFAULT;
	String padChar() default "";
	
	public enum PadAlign {
		LEFT, RIGHT, DEFAULT, NONE
	}

//	Class<? extends TypeConverter> converter() default NULL.class;
//	int counterOrder() default 0;
//	int limit() default 0;
//	Class<? extends Collection> collection() default ArrayList.class;
//
////	String padding() default "L";
//
//	public static abstract class NULL implements TypeConverter<NULL> {}

}
