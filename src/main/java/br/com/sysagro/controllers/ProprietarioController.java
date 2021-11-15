package br.com.sysagro.controllers;

import java.util.List;
import java.util.Optional;

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

import br.com.sysagro.models.Proprietario;
import br.com.sysagro.models.enums.Estado;
import br.com.sysagro.repositories.ProprietarioRepository;
import br.com.sysagro.services.ProprietarioService;
import br.com.sysagro.services.UtilService;
import br.com.sysagro.util.Constantes;

@RequestMapping("/proprietarios")
@Controller
public class ProprietarioController {
	
	@Autowired
	private ProprietarioRepository repository;
	@Autowired
	private ProprietarioService service;
	@Autowired
	private UtilService utilService;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/proprietarios/list");
		view.addObject("proprietarios", service.getProprietarios(txtPesquisa.orElse("")));
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/proprietarios/form");
		view.addObject("proprietario", new Proprietario());
		return view;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/proprietarios/form");
		Proprietario proprietario = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("proprietario", proprietario);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("proprietario") @Valid Proprietario entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/proprietarios/form"); 
		}
		repository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/proprietarios");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		Proprietario proprietario = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		repository.delete(proprietario);
		ModelAndView view = new ModelAndView("redirect:/proprietarios");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
	
	@ModelAttribute("estados")
	public List<Estado> getEstados(){
		return utilService.getEstados();
	}
}
