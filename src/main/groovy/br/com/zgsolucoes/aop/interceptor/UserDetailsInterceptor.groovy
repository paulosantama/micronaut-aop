package br.com.zgsolucoes.aop.interceptor

import br.com.zgsolucoes.aop.annotations.WithUserDetails
import br.com.zgsolucoes.aop.domain.Entidade
import groovy.transform.CompileStatic
import io.micronaut.aop.InterceptorBean
import jakarta.inject.Singleton

@CompileStatic
@Singleton
@InterceptorBean(WithUserDetails)
class UserDetailsInterceptor extends PersistenceInterceptor {

	void executeBeforeSave(Entidade entity) {
		if (isUpdate(entity)) {
			entity.atualizadoPor = 'Paulo'
		} else if (!entity.criadoPor) {
			entity.criadoPor = 'Paulo'
		}
	}

	@SuppressWarnings('GrMethodMayBeStatic')
	boolean isUpdate(Entidade entity) {
		return entity.id != null
	}

}
