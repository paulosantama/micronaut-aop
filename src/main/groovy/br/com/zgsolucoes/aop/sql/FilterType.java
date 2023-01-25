package br.com.zgsolucoes.aop.sql;

/**
 * Enum declarado em Java devido a esse BUG:
 * https://github.com/micronaut-projects/micronaut-openapi/issues/358
 */
public enum FilterType {

	AND("and"),
	OR("or"),
	NONE("");

	private final String clause;

	FilterType(String clause) {
		this.clause = clause;
	}

	public String getClause() {
		return clause;
	}
}
