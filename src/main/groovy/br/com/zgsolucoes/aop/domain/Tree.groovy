package br.com.zgsolucoes.aop.domain

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder

import javax.persistence.Entity

@Entity
@CompileStatic
@Builder(includeSuperProperties = true)
@EqualsAndHashCode(includeFields = true)
@ToString(includePackage = false, includeFields = true, includeNames = true, includeSuperProperties = true)
class Tree extends EntidadeMultiTenant {
	
	String family

	String species
}
