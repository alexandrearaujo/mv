package br.com.mv.liquibase.model;

public enum Versao {
	
	ATUAL("1.42.0");
	
	String descricao;
	
	Versao(String descricao) {
        this.descricao = descricao;
    }
	
	@Override
    public String toString() {
        return descricao;
    }

}
