package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder

@CompileStatic
@Builder
abstract class QueryProcessor<E> {

	Class entity
	List<Parameter> parameters
	Integer offset
	Integer max

	E processQuery() {
		preProcessQuery()
		parameters.stream().forEach { Parameter parameter ->
			if (parameter.isCollection) {
				final Object sampleVal = parameter.valList[0]
				if (sampleVal instanceof Enum) {
					final Collection<String> list = parameter.valList.collect { ((Enum) it).name() }
					setParameterList(parameter, list)
				} else {
					setParameterList(parameter, parameter.valList)
				}
			} else {
				setParameter(parameter)
			}
		}

		return posProcessQuery()
	}

	abstract E posProcessQuery()

	abstract void setParameter(Parameter parameter)

	abstract void setParameterList(Parameter parameter, Collection list)

	abstract void preProcessQuery()

}
