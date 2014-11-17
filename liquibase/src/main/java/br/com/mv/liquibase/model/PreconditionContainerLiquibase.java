package br.com.mv.liquibase.model;

import liquibase.precondition.core.PreconditionContainer;

public class PreconditionContainerLiquibase extends PreconditionContainer {
	
	@Override
    public String getName() {
        return "preConditions";
    }

}
