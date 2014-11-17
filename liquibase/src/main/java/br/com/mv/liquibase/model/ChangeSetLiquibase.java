package br.com.mv.liquibase.model;

import java.util.Set;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.ObjectQuotingStrategy;

public class ChangeSetLiquibase extends ChangeSet {
	
	@Override
	public Set<String> getSerializableFields() {
		Set<String> serializableFields = super.getSerializableFields();
		serializableFields.add("preconditions");
		return serializableFields;
	}
	
	@Override
	public Object getSerializableFieldValue(String field) {
		if (field.equals("preconditions")) {
        	return getPreconditions();
        }
		
		return super.getSerializableFieldValue(field);
	}
	

	public ChangeSetLiquibase(DatabaseChangeLog databaseChangeLog) {
		super(databaseChangeLog);
	}
	
	public ChangeSetLiquibase(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextList, String dbmsList,
			DatabaseChangeLog databaseChangeLog) {
        super(id, author, alwaysRun, runOnChange, filePath, contextList, dbmsList, true, ObjectQuotingStrategy.LEGACY, databaseChangeLog);
    }

    public ChangeSetLiquibase(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextList, String dbmsList,
    		boolean runInTransaction, DatabaseChangeLog databaseChangeLog) {
    	super(id, author, alwaysRun, runOnChange, filePath, contextList, dbmsList, runInTransaction, ObjectQuotingStrategy.LEGACY, databaseChangeLog);
    }

    public ChangeSetLiquibase(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextList, String dbmsList,
    		ObjectQuotingStrategy quotingStrategy, DatabaseChangeLog databaseChangeLog) {
    	super(id, author, alwaysRun, runOnChange, filePath, contextList, dbmsList, true, quotingStrategy, databaseChangeLog);
    }

    public ChangeSetLiquibase(String id, String author, boolean alwaysRun, boolean runOnChange, String filePath, String contextList, String dbmsList,
                     boolean runInTransaction, ObjectQuotingStrategy quotingStrategy, DatabaseChangeLog databaseChangeLog) {
        super(id, author, alwaysRun, runOnChange, filePath, contextList, dbmsList, runInTransaction, quotingStrategy, databaseChangeLog);
    }

}
