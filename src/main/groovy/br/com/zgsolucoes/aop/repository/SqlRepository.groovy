package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.sql.*
import groovy.transform.CompileStatic
import io.micronaut.core.beans.BeanIntrospection
import io.micronaut.core.beans.BeanProperty
import org.hibernate.type.Type

@CompileStatic
abstract class SqlRepository<T extends SqlCommand> {

	QueryModel makeQueryModel(Long tenantId, T command, Closure<String> getQuery) {
		final BeanIntrospection<? extends T> introspection = BeanIntrospection.getIntrospection(command.class as Class<? extends T>)
		final Collection<BeanProperty<? extends T, Object>> properties = introspection.getBeanProperties() as Collection<BeanProperty<? extends T, Object>>
		Map<Group, List<Filter>> filtersByGroup = [:]
		List<Parameter> parameters = []
		properties.forEach { BeanProperty beanProperty ->
			QueryFilter queryFilter = beanProperty.synthesizeDeclared(QueryFilter)
			if (queryFilter != null) {
				Group group = queryFilter.group()
				Object value = convertValue(beanProperty.get(command))
				if (value == null && queryFilter.ignoreIfNull()) {
					return
				}
				String name = queryFilter.paramName() ?: beanProperty.name
				parameters << (value instanceof Collection ? buildParameterList(name, value as Collection) : buildParameter(name, value, null, queryFilter.isLike()))
				List<Filter> filters = treatFilters(filtersByGroup, group)
				filters << buildFilter(queryFilter.value(), queryFilter.type())
			}
		}

		List<Filter> filters = addTenantFilter(filtersByGroup, command)

		SqlCommand.TenantIdFilterInfo tenantIdFilterInfo = command.tenantIdFilterInfo
		if (tenantIdFilterInfo) {
			parameters << buildParameter('tenantId', tenantId, null)
			String tenantFilter = command.tenantIdFilterInfo.filter
			filters << buildFilter(tenantFilter, command.tenantIdFilterInfo.type)
		}

		Map<Group, FilterGroup> filterGroupMap = getFilterGroupMap(introspection)

		List<FilterGroup> filterGroups = getFilterGroups(filtersByGroup, filterGroupMap)

		return new QueryModel().tap {
			baseQuery = getQuery(command)
			it.parameters = parameters
			it.filterGroups = filterGroups
		}
	}

	private List<Filter> addTenantFilter(LinkedHashMap<Group, List<Filter>> filtersByGroup, T command) {
		final Group group = command.tenantIdFilterInfo.group
		return treatFilters(filtersByGroup, group)
	}

	private List<FilterGroup> getFilterGroups(LinkedHashMap<Group, List<Filter>> filtersByGroup, Map<Group, FilterGroup> filterGroupMap) {
		filtersByGroup.entrySet().collect { Map.Entry<Group, List<Filter>> entry ->
			FilterGroup group = filterGroupMap[entry.key]
			if (!group) {
				throw new IllegalArgumentException("O FilterGroup($entry.key.name) não foi encontrado, verifique se ele não foi definido em FilterGroups!")
			}
			group.filters = entry.value
			return group
		}
	}

	private Map<Group, FilterGroup> getFilterGroupMap(BeanIntrospection<? extends T> introspection) {
		FilterGroups queryFilter = introspection.synthesizeDeclared(FilterGroups)
		if (queryFilter) {
			FilterGroupDefinition[] definitions = queryFilter.value()
			return definitions.collectEntries { FilterGroupDefinition definition ->
				FilterGroup group = new FilterGroup().tap {
					type = definition.filterGroupType()
					replacementFilter = definition.value()
				}
				return new MapEntry(group.replacementFilter, group)
			} as Map<Group, FilterGroup>
		}
		return [
				(Group.FILTERS)         : new FilterGroup().tap {
					type = FilterGroupType.WHERE
					replacementFilter = Group.FILTERS
				},
				(Group.INTERNAL_FILTERS): new FilterGroup().tap {
					type = FilterGroupType.WHERE
					replacementFilter = Group.INTERNAL_FILTERS
				},
				(Group.EXTERNAL_FILTERS): new FilterGroup().tap {
					type = FilterGroupType.WHERE
					replacementFilter = Group.EXTERNAL_FILTERS
				}
		]
	}

	private Filter buildFilter(String filter, FilterType type) {
		Filter.builder()
				.filter(filter)
				.type(type)
				.build()
	}

	private Parameter buildParameter(String name, Object val, Type type, Boolean isLike = false) {
		Parameter.builder()
				.name(name)
				.val(val)
				.type(type)
				.isLike(isLike)
				.build()
	}

	private Parameter buildParameterList(String name, Collection val) {
		Parameter.builder()
				.name(name)
				.valList(val)
				.isCollection(true)
				.build()
	}

	private convertValue(Object o) {
		if (o instanceof Enum) {
			return o.name()
		}
		return o
	}

	private static List<Filter> treatFilters(Map<Group, List<Filter>> filtersByGroup, Group group) {
		List<Filter> filters = filtersByGroup[group]
		if (filters == null) {
			filters = []
			filtersByGroup[group] = filters
		}

		return filters
	}

}
