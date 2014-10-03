package br.com.mv.liquibase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import liquibase.exception.LiquibaseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.mv.liquibase.model.ColumnConfigLiquibase;
import br.com.mv.liquibase.model.CreateTableForm;
import br.com.mv.liquibase.model.GrupoParametro;
import br.com.mv.liquibase.model.Parametro;
import br.com.mv.liquibase.service.CreateParametroService;
import br.com.mv.liquibase.service.CreateTableService;
import br.com.mv.liquibase.service.LiquibaseDatabaseService;
import br.com.mv.liquibase.service.ParametroService;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

@Controller
public class LiquibaseController {
	
	@Autowired
    private CreateTableService createTableService;
	
	@Autowired
	private CreateParametroService createParametroService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private LiquibaseDatabaseService liquibaseDatabaseService;
	
	private CreateTableForm createTableForm;
	
	
	
	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@RequestMapping("/createTable")
    public ModelAndView createTable() {
		createTableForm = new CreateTableForm();
		
		createTableForm.getCreateTableChange().setSchemaName("DBAMVFOR");
		createTableForm.getCreateTableChange().setTablespace("TS_MVSISS_D");
		
		createTableForm.getCreateSequenceChange().setCycle(false);
		createTableForm.getCreateSequenceChange().setStartValue(new BigInteger("1"));
		createTableForm.getCreateSequenceChange().setSchemaName("DBAMVFOR");
		createTableForm.getCreateSequenceChange().setIncrementBy(new BigInteger("1"));
		createTableForm.getCreateSequenceChange().setMinValue(new BigInteger("1"));
		createTableForm.getCreateSequenceChange().setMaxValue(new BigInteger("9999999999"));
		
		ModelAndView modelAndView = new ModelAndView("createTable", "createTableForm", createTableForm);
		
        return modelAndView;
    }
	
	public void criarGrupoParametrosXML() throws IOException {
		createParametroService.criarGrupoParametrosXML();
	}
	
	@RequestMapping("/createParametro")
    public ModelAndView createParametro() {
		ModelAndView modelAndView = new ModelAndView("createParametro", "parametro", new Parametro());
        return modelAndView;
    }
	
	@ModelAttribute("grupoParametros")
    public List<GrupoParametro> populateGrupoParametros() throws FileNotFoundException {
		XStream xStream = new XStream();
        xStream.alias("grupoParametro", GrupoParametro.class);
        
        List<GrupoParametro> grupoParametros = null;
        try
        {
        	grupoParametros = (List<GrupoParametro>) xStream.fromXML(new FileInputStream(new File("src/main/resources/grupoParametros.xml")));
        }
        catch (StreamException e)
        {
        	grupoParametros = new ArrayList<GrupoParametro>();
        }
        
        return grupoParametros;
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
	
	@RequestMapping(value="/createTable", params={"adicionarColuna"})
    public ModelAndView adicionarColuna(@ModelAttribute("createTableForm") CreateTableForm createTableForm, final BindingResult bindingResult) {
		if (StringUtils.isBlank(createTableForm.getColumnConfig().getDefaultValue())) {
			createTableForm.getColumnConfig().setDefaultValue(null);
		}
		
		if (createTableForm.getColumnConfig().getConstraints() != null) {
			if (StringUtils.isBlank(createTableForm.getColumnConfig().getConstraints().getCheckConstraint())) {
				createTableForm.getColumnConfig().getConstraints().setCheckConstraint(null);
			}
			
			if (StringUtils.isBlank(createTableForm.getColumnConfig().getConstraints().getForeignKeyName())) {
				createTableForm.getColumnConfig().getConstraints().setForeignKeyName(null);
			}
			
			if (StringUtils.isBlank(createTableForm.getColumnConfig().getConstraints().getPrimaryKeyName())) {
				createTableForm.getColumnConfig().getConstraints().setPrimaryKeyName(null);
			}
			
			if (StringUtils.isBlank(createTableForm.getColumnConfig().getConstraints().getReferences())) {
				createTableForm.getColumnConfig().getConstraints().setReferences(null);
			}
			
			if (StringUtils.isBlank(createTableForm.getColumnConfig().getConstraints().getUniqueConstraintName())) {
				createTableForm.getColumnConfig().getConstraints().setUniqueConstraintName(null);
			}
		}
		
		createTableForm.getCreateTableChange().getColumns().add(createTableForm.getColumnConfig());
		createTableForm.setColumnConfig(new ColumnConfigLiquibase());
		
		ModelAndView modelAndView = new ModelAndView("createTable", "createTableForm", createTableForm);
		
        return modelAndView;
    }

	@RequestMapping(value="/createTable", params={"removerColuna"})
    public ModelAndView removerColuna(@ModelAttribute("createTableForm") CreateTableForm createTableForm,
    		final BindingResult bindingResult, final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removerColuna"));
        createTableForm.getCreateTableChange().getColumns().remove(rowId.intValue());
        
        ModelAndView modelAndView = new ModelAndView("createTable", "createTableForm", createTableForm);
        
        return modelAndView;
    }
	
	@RequestMapping(value="/createTable", params={"salvar"})
    public String salvar(@ModelAttribute("createTableForm") CreateTableForm createTableForm, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "createTable";
        }
        
        createTableService.salvar(createTableForm);
        model.clear();
        return "redirect:/createTable";
    }


	
	@ModelAttribute("createTableForm")
	public CreateTableForm getCreateTableForm() {
		return createTableForm;
	}

	public void setCreateTableForm(CreateTableForm createTableForm) {
		this.createTableForm = createTableForm;
	}

}
