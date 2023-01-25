package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder

@CompileStatic
@Builder
class Filter {

	private static final String SPACE = ' '
	private static final String LEFT_PARENTHESE = '('
	private static final String RIGHT_PARENTHESE = ')'

	String filter
	FilterType type

	String buildFilter() {
		return SPACE.concat(type.clause).concat(LEFT_PARENTHESE).concat(SPACE).concat(filter).concat(SPACE).concat(RIGHT_PARENTHESE).concat(SPACE)
	}

}
