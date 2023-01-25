package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.domain.Car
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
@CompileStatic
interface CarroRepository extends CrudRepository<Car, UUID> {

}