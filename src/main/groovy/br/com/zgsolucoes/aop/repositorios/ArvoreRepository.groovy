package br.com.zgsolucoes.aop.repositorios

import br.com.zgsolucoes.aop.dominio.Tree
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
@CompileStatic
interface ArvoreRepository extends CrudRepository<Tree, UUID> {

}