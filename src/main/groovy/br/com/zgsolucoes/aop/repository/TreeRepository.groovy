package br.com.zgsolucoes.aop.repository

import br.com.zgsolucoes.aop.domain.Tree
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
@CompileStatic
interface TreeRepository extends IRepositorioMultiTenant<Tree>, CrudRepository<Tree, UUID> {

}