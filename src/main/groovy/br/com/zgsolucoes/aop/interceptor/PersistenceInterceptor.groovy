package br.com.zgsolucoes.aop.interceptor

import br.com.zgsolucoes.aop.domain.Entidade
import groovy.transform.CompileStatic
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import io.micronaut.aop.chain.MethodInterceptorChain
import io.micronaut.core.annotation.Nullable

@CompileStatic
abstract class PersistenceInterceptor implements MethodInterceptor<Object, Object> {

	int order = HIGHEST_PRECEDENCE

	@Nullable
	@Override
	Object intercept(MethodInvocationContext<Object, Object> context) {
		if (context instanceof MethodInterceptorChain && context.getMethodName() in saveMethodNames) {
			Object entity = getRequiredEntity(context)
			executeBeforeSave(entity)
		}
		proceed(context)
	}

	Object proceed(MethodInvocationContext<Object, Object> context) {
		return context.proceed()
	}

	@SuppressWarnings('GrMethodMayBeStatic')
	Object getRequiredEntity(MethodInvocationContext<Object, Object> context) {
		return context.parameterValues[0]
	}

	@SuppressWarnings('GrMethodMayBeStatic')
	Set<String> getSaveMethodNames() {
		return new HashSet<String>(['save', 'saveAll', 'update'])
	}

	abstract void executeBeforeSave(Entidade entity)

	void executeBeforeSave(Iterable entities) {
		for (Object entity in entities) {
			executeBeforeSave(entity)
		}
	}

	void executeBeforeSave(Object entity) {
		if (entity instanceof Entidade) {
			executeBeforeSave(entity)
		} else if (entity instanceof Iterable) {
			executeBeforeSave(entity)
		}
	}

}
