package br.com.mv.liquibase.repository;

import java.util.ArrayList;
import java.util.List;

import liquibase.change.core.CreateTableChange;

import org.springframework.stereotype.Repository;

@Repository
public class CreateTableRepository {

	private final List<CreateTableChange> listCreateTableChange = new ArrayList<CreateTableChange>();
	
	
	
	public List<CreateTableChange> findAll() {
        return new ArrayList<CreateTableChange>(this.listCreateTableChange);
    }

    
    public void add(final CreateTableChange createTableChange) {
        this.listCreateTableChange.add(createTableChange);
    }
}
