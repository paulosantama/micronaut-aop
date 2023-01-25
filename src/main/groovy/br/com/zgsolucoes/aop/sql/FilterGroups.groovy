package br.com.zgsolucoes.aop.sql

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.ANNOTATION_TYPE
import static java.lang.annotation.ElementType.TYPE

@Retention(RetentionPolicy.RUNTIME)
@Target([TYPE, ANNOTATION_TYPE])
@interface FilterGroups {

	FilterGroupDefinition[] value()

}
