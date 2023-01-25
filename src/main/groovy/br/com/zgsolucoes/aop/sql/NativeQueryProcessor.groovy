package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import org.hibernate.query.NativeQuery

@CompileStatic
@Builder(includeSuperProperties = true)
class NativeQueryProcessor extends QueryProcessor<NativeQuery> {

	NativeQuery query

	NativeQuery posProcessQuery() {
		if (offset != null && max != null) {
			query.setFirstResult(offset)
			query.setMaxResults(max)
		}

		return query
	}

	void setParameter(Parameter parameter) {
		if (parameter.type) {
			query.setParameter(parameter.name, parameter.val, parameter.type)
		} else if (parameter.isLike) {
			String val = "%".concat(parameter.val.toString()).concat("%")
			query.setParameter(parameter.name, val)
		} else {
			query.setParameter(parameter.name, parameter.val)
		}
	}

	void setParameterList(Parameter parameter, Collection list) {
		query.setParameterList(parameter.name, list)
	}

	void preProcessQuery() {
		if (entity) {
			query.addEntity(entity)
		}
	}

}
