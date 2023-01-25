package br.com.zgsolucoes.aop.sql

import groovy.transform.CompileStatic
//import io.swagger.v3.oas.annotations.Hidden

@CompileStatic
abstract class SqlCommand {

	static class TenantIdFilterInfo {

		String filter
		Group group
		FilterType type

	}

//	@Hidden
	TenantIdFilterInfo getTenantIdFilterInfo() {
		return new TenantIdFilterInfo().tap {
			filter = 'tenant_id = :tenantId'
			group = Group.FILTERS
			type = FilterType.AND
		}
	}

}
