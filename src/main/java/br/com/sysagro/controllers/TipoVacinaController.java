package br.com.sysagro.controllers;

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

import br.com.sysagro.models.TipoVacina;
import br.com.sysagro.repositories.TipoVacinaRepository;
import br.com.sysagro.util.Constantes;

@RequestMapping("/tipos-vacina")
@Controller
public class TipoVacinaController {
	
	@Autowired
	private TipoVacinaRepository repository;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/tipos/list");
		if (txtPesquisa.isPresent()) {
			view.addObject("tipos", repository.findByDescricaoContaining(txtPesquisa.get().toUpperCase()));
		} else {
			view.addObject("tipos", repository.findAll());
		}
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/tipos/form");
		view.addObject("tipo", new TipoVacina());
		return view;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/tipos/form");
		TipoVacina tipoVacina =  repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("tipo", tipoVacina);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("tipo") @Valid TipoVacina entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/tipos/form"); 
		}
		repository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/tipos-vacina");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		TipoVacina tipoVacina = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		repository.delete(tipoVacina);
		ModelAndView view = new ModelAndView("redirect:/tipos-vacina");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
}
