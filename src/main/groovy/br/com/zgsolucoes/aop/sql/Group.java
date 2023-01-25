package br.com.zgsolucoes.aop.sql;

/**
 * Enum declarado em Java devido a esse BUG:
 * https://github.com/micronaut-projects/micronaut-openapi/issues/358
 */
public enum Group {

	FILTERS("filters"),
	INTERNAL_FILTERS("internalFilters"),
	EXTERNAL_FILTERS("externalFilters");

	private final String name;

	Group(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
