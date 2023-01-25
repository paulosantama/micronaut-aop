package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.sql.FilterCommand
import groovy.transform.CompileStatic
import io.micronaut.transaction.annotation.ReadOnly

import javax.inject.Inject

@CompileStatic
trait RepositorioPadrao<T> {

	@Inject
	SqlCommandRepository sqlCommandRepository

	abstract T save(T entity)

	abstract void deleteById(UUID id)

	abstract Boolean existsById(UUID id)

	abstract T update(T entity)

	@ReadOnly
	Integer count(FilterCommand command) {
		return sqlCommandRepository.count(command)
	}

	List<T> list(FilterCommand command) {
		return null
	}

}
