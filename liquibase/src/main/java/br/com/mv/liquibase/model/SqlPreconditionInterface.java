package br.com.mv.liquibase.model;

import liquibase.precondition.core.SqlPrecondition;

@FunctionalInterface
public interface SqlPreconditionInterface {
		
	default public void addSqlPrecondition(PreconditionContainerLiquibase preconditionContainer) {
		SqlPrecondition sqlPrecondition = new SqlPrecondition();
		sqlPrecondition.setExpectedResult("0");
		sqlPrecondition.setSql(getSqlPrecondition());
		
		preconditionContainer.addNestedPrecondition(sqlPrecondition);
	}

	public String getSqlPrecondition();

}
