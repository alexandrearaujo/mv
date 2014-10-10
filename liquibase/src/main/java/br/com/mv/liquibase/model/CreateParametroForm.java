package br.com.mv.liquibase.model;


public class CreateParametroForm {

	private Parametro parametro;
	private OpcaoParametro opcaoParametro;
	
	
	public Parametro getParametro() {
		return parametro;
	}
	
	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	public OpcaoParametro getOpcaoParametro() {
		return opcaoParametro;
	}
	
	public void setOpcaoParametro(OpcaoParametro opcaoParametro) {
		this.opcaoParametro = opcaoParametro;
	}
}
