package br.com.zgsolucoes.aop.sql

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.TYPE

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface FilterGroupDefinition {

	Group value() default Group.FILTERS

	FilterGroupType filterGroupType() default FilterGroupType.WHERE

}
