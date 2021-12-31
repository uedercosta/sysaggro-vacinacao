package br.com.sysagro.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysagro.models.TipoVacina;
import br.com.sysagro.models.Vacina;
import br.com.sysagro.repositories.TipoVacinaRepository;
import br.com.sysagro.repositories.VacinaRepository;
import br.com.sysagro.services.VacinaService;
import br.com.sysagro.util.Constantes;
import br.com.sysagro.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;

@RequestMapping("/vacinas")
@Controller
public class VacinaController {
	
	@Autowired
	private VacinaRepository repository;
	@Autowired
	private VacinaService service;
	@Autowired
	private TipoVacinaRepository tiposRepository;
	@Autowired
	private ReportsUtil util;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/vacinas/list");
		view.addObject("vacinas", service.getVacinas(txtPesquisa.orElse("")));
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/vacinas/form");
		view.addObject("vacina", new Vacina());
		return view;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/vacinas/form");
		Vacina vacina =  repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("vacina", vacina);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("vacina") @Valid Vacina entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/vacinas/form"); 
		}
		repository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/vacinas");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		Vacina vacina = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		repository.delete(vacina);
		ModelAndView view = new ModelAndView("redirect:/vacinas");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
	
	@ModelAttribute("tipos")
	public List<TipoVacina> getTiposVacina(){
		return tiposRepository.findAll();
	}
		
	@GetMapping("/print")
	public void imprimir(@Nullable Optional<String> txtPesquisa, HttpServletResponse response) throws JRException, SQLException, IOException {
		List<Vacina> vacinas = new ArrayList<Vacina>();
		if (txtPesquisa.isPresent()) {
			vacinas =  service.getVacinas(txtPesquisa.get().toUpperCase());
		} else {
			vacinas = service.getVacinas();
		}
		util.imprimir(response, vacinas, "reportVacinas");
	}

}
