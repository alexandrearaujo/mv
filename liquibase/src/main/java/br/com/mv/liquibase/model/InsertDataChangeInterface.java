package br.com.mv.liquibase.model;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import liquibase.change.ColumnConfig;
import liquibase.change.core.InsertDataChange;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceNextValueFunction;

public interface InsertDataChangeInterface {
	
	
	default void createInsertDataChange(Class<?> clazz, ChangeSetLiquibase changeSet) {
		InsertDataChange insertDataChange = new InsertDataChange();
		insertDataChange.setSchemaName("DBAMVFOR");
		insertDataChange.setTableName(clazz.getAnnotation(Table.class).name());
		
		addColumnInInsertDataChange(insertDataChange, clazz);
		
		changeSet.addChange(insertDataChange);
	}

	default void addColumnInInsertDataChange(InsertDataChange insertDataChange,	Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			try {
				ColumnConfig columnConfig;
				if (field.isAnnotationPresent(Column.class)) {
					columnConfig = new ColumnConfig();
					columnConfig.setName(field.getDeclaredAnnotation(Column.class).name());
					
					if (field.isAnnotationPresent(Id.class)) {
						SequenceNextValueFunction valueSequenceNext = new SequenceNextValueFunction(field.getDeclaredAnnotation(SequenceGenerator.class).sequenceName());
						columnConfig.setValueSequenceNext(valueSequenceNext);
					} else if (isFieldNotInsertable(field)) {
							continue;
					} else {
						field.setAccessible(true);
						Object columnValue = field.get(this);
						if (columnValue instanceof String) {
							columnConfig.setValue(columnValue.toString());
						} else if (columnValue instanceof Boolean) {
							columnConfig.setValueBoolean(Boolean.valueOf(columnValue.toString()));
						} else if (columnValue instanceof Long) {
							columnConfig.setValueNumeric(Long.valueOf(columnValue.toString()));
						}
					}
				} else if (field.isAnnotationPresent(JoinColumn.class)) {
					columnConfig = new ColumnConfig();
					columnConfig.setName(field.getDeclaredAnnotation(JoinColumn.class).name());
					
					if (field.isAnnotationPresent(ManyToOne.class)) {
						DatabaseFunction valueComputedGrupoParametro = new DatabaseFunction("(SELECT " + field.getDeclaredAnnotation(JoinColumn.class).name() + " FROM DBAMVFOR." + field.getType().getAnnotation(Table.class).name() + " WHERE " + getWhereClause(field) + ")");
						columnConfig.setValueComputed(valueComputedGrupoParametro);
					}
				} else {
					continue;
				}
				
				insertDataChange.addColumn(columnConfig);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	boolean isFieldNotInsertable(Field field);

	String getWhereClause(Field field);
}
