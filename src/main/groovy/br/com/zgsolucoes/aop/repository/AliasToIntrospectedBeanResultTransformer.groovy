package br.com.zgsolucoes.aop.repository

import com.google.common.base.CaseFormat
import groovy.transform.CompileStatic
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.beans.BeanProperty
import org.hibernate.transform.ResultTransformer

import java.util.stream.IntStream

@CompileStatic
class AliasToIntrospectedBeanResultTransformer implements ResultTransformer {

	private final Class resultClass
	private final BeanIntrospection beanIntrospection

	AliasToIntrospectedBeanResultTransformer(@NonNull Class resultClass) {
		this.resultClass = resultClass
		this.beanIntrospection = BeanIntrospection.getIntrospection(this.resultClass)
	}

	static AliasToIntrospectedBeanResultTransformer aliasToIntrospectedBean(@NonNull Class resultClass) {
		return new AliasToIntrospectedBeanResultTransformer(resultClass)
	}

	@Override
	Object transformTuple(Object[] tuple, String[] aliases) {
		Object result = beanIntrospection.instantiate()

		IntStream.range(0, aliases.length).forEach { int idx ->
			String propertyName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, aliases[idx])
			Optional<BeanProperty> maybeBeanProperty = beanIntrospection.getProperty(propertyName)

			if (maybeBeanProperty.present) {
				BeanProperty beanProperty = maybeBeanProperty.get()
				Object value = tuple[idx]
				Class propertyType = beanProperty.type

				if (propertyType.isEnum()) {
					Enum enumValue = Enum.valueOf(propertyType as Class<Enum>, String.valueOf(value))
					beanProperty.set(result, enumValue)
				} else if (propertyType.isAssignableFrom(UUID) && value instanceof String) {
					beanProperty.set(result, UUID.fromString(value))
				} else {
					beanProperty.set(result, value)
				}
			}
		}
		return result
	}

	@Override
	List transformList(List collection) {
		return collection
	}

}
