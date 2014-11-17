package br.com.mv.liquibase.model;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.ObjectQuotingStrategy;

public interface ChangeSetLiquibaseInterface extends PreconditionContainerInterface, InsertDataChangeInterface {

	
	default public ChangeSetLiquibase createChangeSet(DatabaseChangeLog databaseChangeLog) {
		ChangeSetLiquibase changeSet = new ChangeSetLiquibase(getIdChangeSetDescription(), "mvfor", false, false, null, null, null, ObjectQuotingStrategy.QUOTE_ONLY_RESERVED_WORDS, databaseChangeLog);
		changeSet.setComments(getChangeSetComments());
		createInsertDataChange(getClass(), changeSet);
		addPrecondition(changeSet);
		databaseChangeLog.addChangeSet(changeSet);
		return changeSet;
	}

	public String getChangeSetComments();

	public String getIdChangeSetDescription();
	
}
