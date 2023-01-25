package br.com.zgsolucoes.aop.tenant

import br.com.zgsolucoes.aop.dominio.EntidadeMultiTenant
import groovy.transform.CompileStatic
import org.hibernate.Filter
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.internal.FilterImpl

import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.PersistenceContext
import java.util.function.Supplier

@CompileStatic
@Singleton
class TenantManager {

	public static final String TENANT_FILTER = 'tenantFilter'
	public static final String TENANT_PARAMETER = 'tenantId'

	@Inject
	Tenants tenants

	@PersistenceContext
	SessionFactory sessionFactory

	void ativarFiltroTenant(Long tenantId) {
		Session session = currentSession()
		Filter filter = session.enableFilter(TENANT_FILTER)
		filter.setParameter(TENANT_PARAMETER, tenantId)
	}

	void desativarFiltroTenant() {
		Session session = currentSession()
		session.disableFilter(TENANT_FILTER)
	}

	Session currentSession() {
		return sessionFactory.currentSession
	}

	def <T> T executeWithTenant(Supplier<T> supplier) {
		Session session = currentSession()
		FilterImpl previousFilter = (FilterImpl) session.getEnabledFilter(TENANT_FILTER)
		Long previousTenant = previousFilter?.getParameter(TENANT_PARAMETER) as Long
		try {
			ativarFiltroTenant(tenants.currentId())
			return supplier.get()
		} finally {
			desativarFiltroTenant()
			if (previousFilter != null) {
				Filter filter = session.enableFilter(TENANT_FILTER)
				filter.setParameter(TENANT_PARAMETER, previousTenant)
			}
		}
	}

	void setTenant(EntidadeMultiTenant entidade) {
		Long tenantId = tenants.currentId()
		if (!tenantId) {
			throw new IllegalStateException("Não foi possivel resolver o tenant")
		}

		entidade.tenantId = tenants.currentId()
	}

}
