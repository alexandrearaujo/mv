package br.com.mv.liquibase.model;

import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateSequenceChange;
import liquibase.change.core.CreateTableChange;

public class CreateTableForm {

	private CreateTableChange createTableChange;
	private CreateIndexChange createIndexChange;
	private CreateSequenceChange createSequenceChange;
	private ColumnConfigLiquibase columnConfig;
	
	
	public CreateTableChange getCreateTableChange() {
		if (createTableChange == null) {
			createTableChange = new CreateTableChange();
		}
		
		return createTableChange;
	}
	
	public void setCreateTableChange(CreateTableChange createTableChange) {
		this.createTableChange = createTableChange;
	}
	
	public CreateIndexChange getCreateIndexChange() {
		if (createIndexChange == null) {
			createIndexChange = new CreateIndexChange();
		}
		
		return createIndexChange;
	}
	
	public void setCreateIndexChange(CreateIndexChange createIndexChange) {
		this.createIndexChange = createIndexChange;
	}
	
	public CreateSequenceChange getCreateSequenceChange() {
		if (createSequenceChange == null) {
			createSequenceChange = new CreateSequenceChange();
		}
			
		return createSequenceChange;
	}
	
	public void setCreateSequenceChange(CreateSequenceChange createSequenceChange) {
		this.createSequenceChange = createSequenceChange;
	}

	public ColumnConfigLiquibase getColumnConfig() {
		if (columnConfig == null)
		{
			columnConfig = new ColumnConfigLiquibase();
		}
		
		return columnConfig;
	}

	public void setColumnConfig(ColumnConfigLiquibase columnConfig) {
		this.columnConfig = columnConfig;
	}

}
