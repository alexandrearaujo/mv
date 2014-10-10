package br.com.mv.liquibase.web;

import java.sql.SQLException;

import liquibase.exception.LiquibaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mv.liquibase.service.LiquibaseDatabaseService;
import br.com.mv.liquibase.service.ParametroService;

@Controller
public class LiquibaseController {
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private LiquibaseDatabaseService liquibaseDatabaseService;
	
	
	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@RequestMapping("/gerarXmlParametro")
	public String gerarXmlParametro() {
		parametroService.gerarXml();
		return "index";
	}
	
	@RequestMapping("/gerarDocumentacao")
	public String gerarDocumentacao() throws SQLException, LiquibaseException {
		liquibaseDatabaseService.testeConexao();
		return "index";
	}	
}