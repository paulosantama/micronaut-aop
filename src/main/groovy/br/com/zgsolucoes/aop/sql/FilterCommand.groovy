package br.com.zgsolucoes.aop.sql

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.beans.BeanProperty

@CompileStatic
@SuppressWarnings('SpaceAroundOperator')
abstract class FilterCommand extends SqlCommand {

	@QueryFilter('criadoEm >= :criadoEm')
	private Date criadoEm = null

	@QueryFilter('atualizadoEm <= :atualizadoEm')
	private Date atualizadoEm = null

	abstract String getBaseQuery(boolean count)

	@JsonIgnore
	abstract String getFilterListQuery()

	@JsonIgnore
	abstract String getTotalsQuery()

	@JsonIgnore
	abstract List<String> getExcludeFilters()

	/**
	 * Método para excluir os filtros não desejados na listagem de opções de um filtro
	 * @return
	 */
	void excludeFilter() {
		if (!excludeFilters) {
			return
		}
		final BeanIntrospection<FilterCommand> introspection = BeanIntrospection.getIntrospection(this.class as Class<FilterCommand>)
		final Collection<BeanProperty<FilterCommand, Object>> properties = introspection.getBeanProperties()
		properties.each {
			if (excludeFilters.contains(it.name)) {
				it.set(this, null)
			}
		}
	}

	Date getCriadoEm() {
		return criadoEm
	}

	void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm
	}

	Date getAtualizadoEm() {
		return atualizadoEm
	}

	void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm
	}

}
