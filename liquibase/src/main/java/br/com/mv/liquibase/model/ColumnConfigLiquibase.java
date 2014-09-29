package br.com.mv.liquibase.model;

import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;

public class ColumnConfigLiquibase extends ColumnConfig {

	
	public Boolean getPrimaryKey() {
		if (super.getConstraints() == null) {
			super.setConstraints(new ConstraintsConfig());
		}
		
		return super.getConstraints().isPrimaryKey();
	}

	public void setPrimaryKey(Boolean primaryKey) {
		super.getConstraints().setPrimaryKey(primaryKey);
	}

	public Boolean getNullable() {
		return super.getConstraints().isNullable();
	}

	public void setNullable(Boolean nullable) {
		super.getConstraints().setNullable(nullable);
	}
	
	public Boolean getUnique() {
		return super.getConstraints().isUnique();
	}
	
	public void setUnique(Boolean unique) {
		super.getConstraints().setUnique(unique);
	}

	
	public ColumnConfigLiquibase() {
		super();
		
		if (super.getConstraints() == null) {
			super.setConstraints(new ConstraintsConfig());
		}
	}
}
