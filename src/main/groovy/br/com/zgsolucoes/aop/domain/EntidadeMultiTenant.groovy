package br.com.zgsolucoes.aop.domain

import br.com.zgsolucoes.aop.tenant.TenantManager
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.Filters
import org.hibernate.annotations.ParamDef

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull

@CompileStatic
@MappedSuperclass
@EqualsAndHashCode(callSuper = true, includeFields = true)
@ToString(includeSuper = true, includePackage = false, includeNames = true, includeFields = true)
@FilterDef(name = TenantManager.TENANT_FILTER, parameters = @ParamDef(name = TenantManager.TENANT_PARAMETER, type = 'long'))
@Filters(
		@Filter(name = TenantManager.TENANT_FILTER, condition = 'tenant_id = :tenantId')
)
abstract class EntidadeMultiTenant extends Entidade {

	@NotNull
	@Column(nullable = false)
	Long tenantId = -1 //Inicializado para o Interceptador poder capturar e setar o tenantId

}
