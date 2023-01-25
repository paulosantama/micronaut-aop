package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.annotations.WithTenant
import br.com.zgsolucoes.aop.annotations.WithUserDetails
import br.com.zgsolucoes.aop.domain.Car
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
@CompileStatic
@WithTenant
@WithUserDetails
interface CarRepository extends CrudRepository<Car, UUID> {

}