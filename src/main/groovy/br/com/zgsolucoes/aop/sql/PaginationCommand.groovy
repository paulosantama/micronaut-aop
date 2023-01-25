package br.com.zgsolucoes.aop.sql

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic
import io.micronaut.core.annotation.Introspected
//import io.swagger.v3.oas.annotations.media.Schema

import javax.validation.constraints.Pattern

@CompileStatic
@Introspected
//@Schema(
//		allOf = [FilterCommand]
//)
abstract class PaginationCommand extends FilterCommand {

	public static final String DEFAULT_SORT = "criadoEm"
	public static final String DEFAULT_ORDER = "desc"

	public static final Integer DEFAULT_PAGE_SIZE = 100L
	public static final Integer DEFAULT_COUNT_START = 0L

//	@Schema(name = 'sort', description = 'Campo referencia para ordenação', required = false)
	String sort = DEFAULT_SORT

	@Pattern(regexp = 'asc|desc')
//	@Schema(name = 'order', description = 'Ordem(asc,desc) de ordenação para o campo informado no parametro sort', required = false, allowableValues = ['asc', 'desc'])
	String order = DEFAULT_ORDER

//	@Schema(name = 'offset', description = 'Index usado para navegar no resultado da consulta', required = false, minimum = '0')
	Integer offset = DEFAULT_COUNT_START

//	@Schema(name = 'max', description = 'Quantidade maxima de ocorrências que sera trazida por consulta', required = false, minimum = '10', maximum = '100')
	Integer max = DEFAULT_PAGE_SIZE

	@JsonIgnore
	Sort getSqlSort() {
		return Sort.builder()
				.sort(this.getSort())
				.order(this.getOrder())
				.build()
	}

	String getSort() {
		return sort
	}

	void setSort(String sort) {
		this.sort = sort
	}

	String getOrder() {
		return order
	}

	void setOrder(String order) {
		this.order = order
	}

	Integer getOffset() {
		return offset
	}

	void setOffset(Integer offset) {
		this.offset = offset
	}

	Integer getMax() {
		return max
	}

	void setMax(Integer max) {
		this.max = max
	}

}
