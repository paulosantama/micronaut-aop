package br.com.zgsolucoes.aop.interceptor

import br.com.zgsolucoes.aop.annotations.WithTenant
import br.com.zgsolucoes.aop.domain.Entidade
import br.com.zgsolucoes.aop.domain.EntidadeMultiTenant
import br.com.zgsolucoes.aop.tenant.TenantManager
import groovy.transform.CompileStatic
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInvocationContext
import jakarta.inject.Singleton

import javax.inject.Inject
import javax.transaction.Transactional

@CompileStatic
@Singleton
@InterceptorBean(WithTenant)
class TenantInterceptor extends PersistenceInterceptor {

	@Inject
	TenantManager tenantManager

	void executeBeforeSave(Entidade entity) {
		if (entity instanceof EntidadeMultiTenant) {
			tenantManager.setTenant(entity)
		}
	}

	@Override
	@Transactional
	Object proceed(MethodInvocationContext<Object, Object> context) {
		return tenantManager.executeWithTenant {
			return super.proceed(context)
		}
	}

}
