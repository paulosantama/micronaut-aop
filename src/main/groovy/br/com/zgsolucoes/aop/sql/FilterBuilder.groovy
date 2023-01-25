package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic

@CompileStatic
trait FilterBuilder {

	static final String SPACE = ' '
	static final String LEFT_PARENTHESE = '('
	static final String RIGHT_PARENTHESE = ')'

	abstract String buildFilter()

}
