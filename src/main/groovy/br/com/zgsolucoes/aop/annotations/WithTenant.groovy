package br.com.zgsolucoes.aop.annotations

import groovy.transform.CompileStatic
import io.micronaut.aop.Around

import java.lang.annotation.*

@CompileStatic
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])
@Around
@interface WithTenant {

}