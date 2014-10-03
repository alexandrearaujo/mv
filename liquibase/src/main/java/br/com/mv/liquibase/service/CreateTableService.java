package br.com.mv.liquibase.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import liquibase.change.AddColumnConfig;
import liquibase.change.ColumnConfig;
import liquibase.change.core.CreateIndexChange;
import liquibase.change.core.CreateTableChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import liquibase.util.SystemUtils;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.liquibase.model.CreateTableForm;
import br.com.mv.liquibase.model.Versao;
import br.com.mv.liquibase.repository.CreateTableRepository;

@Service
public class CreateTableService {

	@Autowired
    private CreateTableRepository createTableRepository;
	
	
	public void add(final CreateTableChange createTableChange) {
        this.createTableRepository.add(createTableChange);
    }


	public void salvar(CreateTableForm createTableForm) {
		DatabaseChangeLog databaseChangeLog = new DatabaseChangeLog();
		ChangeSet changeSet = new ChangeSet(String.valueOf(new Date().getTime()) , SystemUtils.USER_NAME, false, false, null, null, null, true, databaseChangeLog);
		changeSet.setComments("CRIAÇÃO DE TABELA");
		changeSet.addChange(createTableForm.getCreateTableChange());
		changeSet.addChange(createTableForm.getCreateSequenceChange());
		
		for (ColumnConfig columnConfig : createTableForm.getCreateTableChange().getColumns()) {
			if (BooleanUtils.isTrue(columnConfig.getConstraints().isPrimaryKey()) || 
					StringUtils.isNotBlank(columnConfig.getConstraints().getForeignKeyName())) {
				CreateIndexChange createIndexChange = new CreateIndexChange();
				createIndexChange.setTableName(createTableForm.getCreateTableChange().getTableName());
				createIndexChange.setSchemaName(createTableForm.getCreateTableChange().getSchemaName());
				
				AddColumnConfig addColumnConfig = new AddColumnConfig();
				addColumnConfig.setName(columnConfig.getName());
				createIndexChange.addColumn(addColumnConfig);
				createIndexChange.setTablespace("TS_MVSISS_I");
				createIndexChange.setChangeSet(changeSet);
				
				if (BooleanUtils.isTrue(columnConfig.getConstraints().isPrimaryKey())) {
					createIndexChange.setAssociatedWith("primaryKey");
					createIndexChange.setIndexName(columnConfig.getConstraints().getPrimaryKeyName());
					createIndexChange.setUnique(true);
				} else {
					createIndexChange.setIndexName(columnConfig.getConstraints().getForeignKeyName().replace("CNT", "IND"));
					createIndexChange.setAssociatedWith("foreignKey");
					createIndexChange.setUnique(false);
				}
				changeSet.addChange(createIndexChange);
			}
		}
		
		changeSet.addValidCheckSum(changeSet.generateCheckSum().toString());
		
		//databaseChangeLog.setPhysicalFilePath("db/changelog/changeLog.xml");
		databaseChangeLog.addChangeSet(changeSet);
		
		List<ChangeSet> listChangeSet = new ArrayList<ChangeSet>();
		listChangeSet.add(changeSet);
		
		try (OutputStream os = new FileOutputStream("src/main/resources/db/changelog/tables/" + Versao.getVersaoAtual() + "_" + LocalDate.now()  + "_create_table_" + createTableForm.getCreateTableChange().getTableName())) {
			XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
			xmlChangeLogSerializer.write(listChangeSet, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
