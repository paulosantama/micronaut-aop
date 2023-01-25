package br.com.zgsolucoes.aop.sql

import com.google.common.base.CaseFormat
import groovy.transform.CompileStatic
import groovy.transform.builder.Builder

@CompileStatic
@Builder
class Sort {

	private static final String PREFIX = ' order by '
	private static final String SPACE = ' '
	private static final String DEFAULT_ORDER = 'ASC'

	String sort
	String order = DEFAULT_ORDER

	String buildOrderBy() {
		String sort = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sort)
		return PREFIX.concat(sort).concat(SPACE).concat(order).concat(SPACE)
	}

}
