package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.sql.*
import br.com.zgsolucoes.aop.tenant.Tenants
import groovy.transform.CompileStatic
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.data.jpa.operations.JpaRepositoryOperations
import org.hibernate.Session
import org.hibernate.query.NativeQuery

import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.Entity
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Singleton
@CompileStatic
class SqlCommandRepository extends SqlRepository<FilterCommand> {

	@Inject
	JpaRepositoryOperations operations

	@Inject
	Tenants tenants

	def <T> List<T> list(@NotNull @Valid FilterCommand command, Class<T> clazz = null) {
		QueryModel queryModel = makeQueryModel(tenants.currentId(), command) { FilterCommand c ->
			return c.getBaseQuery(true)
		}

		Sort sort = null
		Integer offset = null
		Integer max = null
		if (command instanceof PaginationCommand) {
			sort = command.sqlSort
			offset = command.offset
			max = command.max
		}

		SqlBuilder sqlBuilder = SqlBuilder.builder()
				.base(command.getBaseQuery(false))
				.filtersGroup(queryModel.filterGroups)
				.sort(sort)
				.build()

		String sql = sqlBuilder.prepareSql()

		final Session session = operations.currentEntityManager.unwrap(Session)

		NativeQuery query = session.createSQLQuery(sql)
		if (clazz != null) {
			final BeanIntrospection introspection = BeanIntrospection.getIntrospection(clazz)
			if (introspection.hasAnnotation(Entity)) {
				query.addEntity(clazz)
			} else {
				query.setResultTransformer(AliasToIntrospectedBeanResultTransformer.aliasToIntrospectedBean(clazz))
			}
		}

		NativeQueryProcessor queryProcessor = NativeQueryProcessor.builder()
				.query(query)
				.parameters(queryModel.parameters)
				.offset(offset)
				.max(max)
				.build()

		return queryProcessor.<T> processQuery().resultList
	}

	Integer count(@NotNull @Valid FilterCommand command) {
		QueryModel queryModel = makeQueryModel(tenants.currentId(), command) { FilterCommand c ->
			return c.getBaseQuery(false)
		}

		SqlBuilder sqlBuilder = SqlBuilder.builder()
				.base(command.getBaseQuery(true))
				.filtersGroup(queryModel.filterGroups)
				.build()

		String sql = sqlBuilder.prepareSql()

		final Session session = operations.currentEntityManager.unwrap(Session)

		NativeQuery query = session.createSQLQuery(sql)
		NativeQueryProcessor queryProcessor = NativeQueryProcessor.builder()
				.query(query)
				.parameters(queryModel.parameters)
				.build()
		return queryProcessor.processQuery().singleResult as Integer
	}

//	def <T> T totais(@NotNull @Valid FilterCommand command, Class<T> clazz = null) {
//		QueryModel queryModel = makeQueryModel(tenants.currentId(), command) { FilterCommand c ->
//			return c.getBaseQuery(false)
//		}
//
//		SqlBuilder sqlBuilder = SqlBuilder.builder()
//				.base(command.getTotalsQuery())
//				.filtersGroup(queryModel.filterGroups)
//				.build()
//
//		String sql = sqlBuilder.prepareSql()
//
//		final Session session = operations.currentEntityManager.unwrap(Session)
//
//		NativeQuery query = session.createSQLQuery(sql)
//		if (clazz != null) {
//			final BeanIntrospection introspection = BeanIntrospection.getIntrospection(clazz)
//			if (introspection.hasAnnotation(Entity)) {
//				query.addEntity(clazz)
//			} else {
//				query.setResultTransformer(AliasToIntrospectedBeanResultTransformer.aliasToIntrospectedBean(clazz))
//			}
//		}
//
//		NativeQueryProcessor queryProcessor = NativeQueryProcessor.builder()
//				.query(query)
//				.parameters(queryModel.parameters)
//				.build()
//
//		return queryProcessor.<T> processQuery().uniqueResult() as T
//	}
//
//	def <T> List<T> filterList(@NotNull @Valid FilterCommand command) {
//		command.excludeFilter()
//		QueryModel queryModel = makeQueryModel(tenants.currentId(), command) { FilterCommand c ->
//			return c.getBaseQuery(false)
//		}
//
//		SqlBuilder sqlBuilder = SqlBuilder.builder()
//				.base(command.getFilterListQuery())
//				.filtersGroup(queryModel.filterGroups)
//				.build()
//
//		String sql = sqlBuilder.prepareSql()
//
//		final Session session = operations.currentEntityManager.unwrap(Session)
//
//		NativeQuery query = session.createSQLQuery(sql)
//		query.setResultTransformer(AliasToIntrospectedBeanResultTransformer.aliasToIntrospectedBean(FiltroDto))
//
//		NativeQueryProcessor queryProcessor = NativeQueryProcessor.builder()
//				.query(query)
//				.parameters(queryModel.parameters)
//				.build()
//
//		return queryProcessor.<T> processQuery().resultList
//	}

}
