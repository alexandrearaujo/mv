package br.com.mv.liquibase.web;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.mv.liquibase.model.ColumnConfigLiquibase;
import br.com.mv.liquibase.model.CreateTableForm;
import br.com.mv.liquibase.service.CreateTableService;

@Controller
public class CreateTableController {

	@Autowired
    private CreateTableService createTableService;
	
	private CreateTableForm createTableForm;
	
	
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
	
	@RequestMapping(value="/createTable", method = RequestMethod.POST)
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
