package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder

@CompileStatic
@Builder
class SqlBuilder {

	String base
	List<FilterGroup> filtersGroup
	Sort sort

	String prepareSql() {
		String preparedSql = new String(base)
		filtersGroup.stream().forEach { FilterGroup filterGroup ->
			final String sqlFilter = filterGroup.buildFilter()
			preparedSql = preparedSql.replaceAll(/(\/\*)?\{${filterGroup.replacementFilter.name}\}(\*\/)?/, sqlFilter)
		}

		if (sort != null) {
			preparedSql = preparedSql.concat(sort.buildOrderBy())
		}

		return preparedSql
	}

}
