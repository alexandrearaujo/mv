package br.com.mv.liquibase.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.liquibase.model.GrupoParametro;
import br.com.mv.liquibase.model.OpcaoParametro;
import br.com.mv.liquibase.model.Parametro;
import br.com.mv.liquibase.model.Versao;
import br.com.mv.liquibase.repository.GrupoParametroRepository;
import br.com.mv.liquibase.repository.ParametroRepository;

@Service
public class ParametroService {

	@Autowired
	ParametroRepository parametroRepository;
	
	@Autowired
	GrupoParametroRepository grupoParametroRepository;
	
	protected DatabaseChangeLog databaseChangeLog = new DatabaseChangeLog();
	protected List<ChangeSet> listChangeSet = new ArrayList<>();
	
	
	public void gerarXml() {
		List<GrupoParametro> grupoParametros = grupoParametroRepository.findAll();
		
		for (GrupoParametro grupoParametro : grupoParametros) {
			listChangeSet.add(grupoParametro.createChangeSet(databaseChangeLog));
		}
		
		List<Parametro> parametros = parametroRepository.findAll();
		
		for (Parametro parametro : parametros) {
			createChangeSetParametroAndOpcaoParametro(parametro);
		}
		
		serializeXMLParametro();
		databaseChangeLog = new DatabaseChangeLog();
		listChangeSet = new ArrayList<>();
	}

	protected void createChangeSetParametroAndOpcaoParametro(Parametro parametro) {
		listChangeSet.add(parametro.createChangeSet(databaseChangeLog));
		
		parametro.getOpcoesParametros().size();
		
		for (OpcaoParametro opcaoParametro : parametro.getOpcoesParametros()) {
			listChangeSet.add(opcaoParametro.createChangeSet(databaseChangeLog));
		}
	}
	
	protected void serializeXMLParametro() {
		try (OutputStream os = new FileOutputStream("src/main/resources/db/changelog/inserts/" + Versao.getVersaoAtual() + "_" + LocalDate.now() + "_create_parametros.xml")) {
			XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
			xmlChangeLogSerializer.write(listChangeSet, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
