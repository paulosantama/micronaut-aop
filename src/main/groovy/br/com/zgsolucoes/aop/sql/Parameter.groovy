package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import org.hibernate.type.Type

@CompileStatic
@Builder
class Parameter {

	String name
	Object val
	Collection valList
	Type type
	boolean isCollection = false
	boolean isLike = false

}
