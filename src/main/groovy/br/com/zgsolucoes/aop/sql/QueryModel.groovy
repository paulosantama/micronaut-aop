package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@EqualsAndHashCode
@ToString(includePackage = false, includeFields = true)
class QueryModel {

	String baseQuery
	List<Parameter> parameters
	List<FilterGroup> filterGroups

}
