package br.com.zgsolucoes.aop.sql

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.FIELD

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface QueryFilter {

	String value() default ''

	boolean ignoreIfNull() default true

	FilterType type() default FilterType.AND

	Group group() default Group.FILTERS

	String paramName() default ''

	boolean isLike() default false

}
