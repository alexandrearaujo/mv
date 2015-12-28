package br.com.mv.liquibase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

import br.com.mv.liquibase.model.CreateParametroForm;
import br.com.mv.liquibase.model.GrupoParametro;
import br.com.mv.liquibase.model.OpcaoParametro;
import br.com.mv.liquibase.model.Parametro;
import br.com.mv.liquibase.service.CreateParametroService;

@Controller
@SessionAttributes(types = CreateParametroForm.class)
@RequestMapping(value = "/createParametro")
public class CreateParametroController {
	
	@Autowired
	private CreateParametroService createParametroService; 
	
	private CreateParametroForm createParametroForm;
	
	private List<GrupoParametro> grupoParametros = null;

	
	@RequestMapping(method = RequestMethod.GET)
    public String createParametro(Model model) {
		createParametroForm = new CreateParametroForm();
		createParametroForm.setOpcaoParametro(new OpcaoParametro());
		Parametro parametro = new Parametro();
		parametro.setOpcoesParametros(new ArrayList<OpcaoParametro>());
		parametro.setGrupoParametro(new GrupoParametro());
		createParametroForm.setParametro(parametro);
		model.addAttribute("createParametroForm", createParametroForm);
        return "createParametro";
    }
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("grupoParametros")
    public List<GrupoParametro> populateGrupoParametros() throws FileNotFoundException {
		XStream xStream = new XStream();
        xStream.alias("grupoParametro", GrupoParametro.class);
        
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
	
	public void criarGrupoParametrosXML() throws IOException {
		createParametroService.criarGrupoParametrosXML();
	}
	
	@RequestMapping(value = {"/adicionarOpcaoParametro"})
    public ModelAndView adicionarOpcaoParametro(@ModelAttribute("createParametroForm") CreateParametroForm createParametroForm,
    	    final BindingResult bindingResult) {
		createParametroForm.getParametro().getOpcoesParametros().add(createParametroForm.getOpcaoParametro());
		ModelAndView modelAndView = new ModelAndView("createParametro", "createParametroForm", createParametroForm);
		createParametroForm.setOpcaoParametro(new OpcaoParametro());
		
        return modelAndView;
    }

	@RequestMapping(value="/createParametro/removerOpcaoParametro")
    public ModelAndView removerColuna(CreateParametroForm createParametroForm,
    		final BindingResult bindingResult, final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("indexOpcaoParametro"));
        createParametroForm.getParametro().getOpcoesParametros().remove(rowId.intValue());
        
        ModelAndView modelAndView = new ModelAndView("createParametro", "createParametroForm", createParametroForm);
        
        return modelAndView;
    }
	
    @RequestMapping("/createParametro/removerOpcaoParametro/{indexOpcaoParametro}")
    public ModelAndView removerOpcaoParametro(@ModelAttribute("createParametroForm") CreateParametroForm createParametroForm,
    		final BindingResult bindingResult, @PathVariable int indexOpcaoParametro) {
        createParametroForm.getParametro().getOpcoesParametros().remove(indexOpcaoParametro);
        ModelAndView modelAndView = new ModelAndView("createParametro", "createParametroForm", createParametroForm);
        return modelAndView;
    }
	
	
	@RequestMapping(method = RequestMethod.POST)
    public String salvar(@Valid @ModelAttribute CreateParametroForm createParametroForm,
    		final BindingResult bindingResult, final ModelMap model, SessionStatus status) {
        if (bindingResult.hasErrors()) {
            return "createParametro";
        }
        
		createParametroForm.getParametro()
				.setGrupoParametro(grupoParametros.stream()
						.filter(grupoParametro -> grupoParametro.getId()
								.equals(createParametroForm.getParametro().getGrupoParametro().getId()))
						.findFirst().get());
        
        createParametroService.salvar(createParametroForm);
        model.clear();
        status.setComplete();
        return "redirect:/createParametro";
    }

	@ModelAttribute("createParametroForm")
	public CreateParametroForm getCreateParametroForm() {
		return createParametroForm;
	}

	public void setCreateParametroForm(CreateParametroForm createParametroForm) {
		this.createParametroForm = createParametroForm;
	}
	
}