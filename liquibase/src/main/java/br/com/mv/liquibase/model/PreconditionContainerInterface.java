package br.com.mv.liquibase.model;

import liquibase.precondition.core.PreconditionContainer;

public interface PreconditionContainerInterface extends	SqlPreconditionInterface {

	default public void addPrecondition(ChangeSetLiquibase changeSet) {
		PreconditionContainerLiquibase preconditionContainer = new PreconditionContainerLiquibase();
		preconditionContainer.setOnFail(PreconditionContainer.FailOption.MARK_RAN.toString());	
		addSqlPrecondition(preconditionContainer);
		changeSet.setPreconditions(preconditionContainer);
	}
}
