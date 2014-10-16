package br.com.mv.liquibase.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.liquibase.model.CreateParametroForm;
import br.com.mv.liquibase.model.GrupoParametro;
import br.com.mv.liquibase.repository.GrupoParametroRepository;

import com.thoughtworks.xstream.XStream;

@Service
public class CreateParametroService {

	@Autowired
    private GrupoParametroRepository grupoParametroRepository;
	
	
	public void criarGrupoParametrosXML() throws IOException {
		File file = new File("src/main/resources/grupoParametros.xml");
		file.createNewFile();
		
		List<GrupoParametro> grupoParametros = grupoParametroRepository.findAll();
		
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        XStream xStream = new XStream();
        xStream.alias("grupoParametro", GrupoParametro.class);
        xStream.toXML(grupoParametros, outputStream);
    }


	public void salvar(CreateParametroForm createParametroForm) {
		// TODO Auto-generated method stub
		
	}
}
