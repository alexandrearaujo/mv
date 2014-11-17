package br.com.mv.liquibase.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import liquibase.changelog.DatabaseChangeLog;

import org.springframework.stereotype.Service;

import br.com.mv.liquibase.model.CreateParametroForm;
import br.com.mv.liquibase.model.GrupoParametro;

import com.thoughtworks.xstream.XStream;

@Service
public class CreateParametroService extends ParametroService {

	public void criarGrupoParametrosXML() throws IOException {
		File file = new File("src/main/resources/grupoParametros.xml");
		file.createNewFile();
		
		List<GrupoParametro> grupoParametros = grupoParametroRepository.findAll();
		
        try (OutputStream os = new FileOutputStream(file)) {
            XStream xStream = new XStream();
            xStream.alias("grupoParametro", GrupoParametro.class);
            xStream.toXML(grupoParametros, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	public void salvar(CreateParametroForm createParametroForm) {
		createChangeSetParametroAndOpcaoParametro(createParametroForm.getParametro());
		serializeXMLParametro();
		listChangeSet = new ArrayList<>();
		databaseChangeLog = new DatabaseChangeLog();
	}
}
