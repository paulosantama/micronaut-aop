package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic

@CompileStatic
enum FilterGroupType {

	AND('and'),
	WHERE('where')

	final String clause

	FilterGroupType(String clause) {
		this.clause = clause
	}

}
