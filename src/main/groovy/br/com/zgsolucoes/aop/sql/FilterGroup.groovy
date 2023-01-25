package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder

@CompileStatic
@Builder
class FilterGroup implements FilterBuilder {

	List<Filter> filters
	FilterGroupType type
	Group replacementFilter

	@Override
	String buildFilter() {
		StringBuffer stringBuffer = new StringBuffer(type.clause)
		stringBuffer.append(SPACE)
		filters[0]?.type = FilterType.NONE
		filters.stream().forEach { Filter filter ->
			stringBuffer.append(filter.buildFilter())
		}
		return stringBuffer.toString()
	}

}
