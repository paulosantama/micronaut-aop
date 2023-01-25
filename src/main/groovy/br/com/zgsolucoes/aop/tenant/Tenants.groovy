package br.com.zgsolucoes.aop.tenant

import groovy.transform.CompileStatic
import io.micronaut.multitenancy.tenantresolver.TenantResolver

import javax.inject.Inject
import javax.inject.Singleton

@CompileStatic
@Singleton
class Tenants {

	@Inject
	TenantResolver tenantResolver

	static <T> T withId(Serializable id, Closure<T> callable) {
		return CurrentTenant.withTenant(id, callable)
	}

	Long currentId() {
		return (CurrentTenant.get() ?: tenantResolver.resolveTenantIdentifier()) as Long
	}

	@CompileStatic
	protected static class CurrentTenant {

		private static final ThreadLocal<Serializable> CURRENT_TENANT_THREAD_LOCAL = new ThreadLocal<>()

		/**
		 * Execute with the current tenant
		 *
		 * @param callable The closure
		 * @return The result of the closure
		 */
		static <T> T withTenant(Serializable tenantId, Closure<T> callable) {
			Serializable previous = get()
			try {
				set(tenantId)
				callable.call(tenantId)
			} finally {
				if (previous == null) {
					remove()
				} else {
					set(previous)
				}
			}
		}

		/**
		 * @return Obtain the current tenant
		 */
		static Serializable get() {
			return CURRENT_TENANT_THREAD_LOCAL.get()
		}

		/**
		 * Set the current tenant
		 *
		 * @param tenantId The tenant id
		 */
		private static void set(Serializable tenantId) {
			CURRENT_TENANT_THREAD_LOCAL.set(tenantId)
		}

		private static void remove() {
			CURRENT_TENANT_THREAD_LOCAL.remove()
		}

	}

}
