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

import br.com.sysagro.models.Animal;
import br.com.sysagro.models.Fazenda;
import br.com.sysagro.models.Proprietario;
import br.com.sysagro.models.Raca;
import br.com.sysagro.models.enums.Sexo;
import br.com.sysagro.models.enums.StatusAnimal;
import br.com.sysagro.repositories.AnimalRepository;
import br.com.sysagro.repositories.FazendaRepository;
import br.com.sysagro.repositories.ProprietarioRepository;
import br.com.sysagro.repositories.RacaRepository;
import br.com.sysagro.services.AnimalService;
import br.com.sysagro.services.UtilService;
import br.com.sysagro.util.Constantes;

@RequestMapping("/animais")
@Controller
public class AnimalController {
	
	@Autowired
	private AnimalRepository repository;
	@Autowired
	private AnimalService animalService;
	@Autowired
	private RacaRepository racaRepository;
	@Autowired
	private ProprietarioRepository propRepository;
	@Autowired
	private FazendaRepository fazendaRepository;
	@Autowired
	private UtilService utilService;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/animais/list");
		view.addObject("animais", animalService.getAnimais(txtPesquisa.orElse("")));
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/animais/form");
		view.addObject("animal", new Animal());
		return view;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/animais/form");
		Animal animal = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("animal", animal);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("animal") @Valid Animal entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/animais/form"); 
		}
		repository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/animais");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		Animal animal = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		repository.delete(animal);
		ModelAndView view = new ModelAndView("redirect:/animais");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
	
	@ModelAttribute("racas")
	public List<Raca> getRacas(){
		return racaRepository.findAll();
	}
	
	@ModelAttribute("proprietarios")
	public List<Proprietario> getProprietarios(){
		return propRepository.findAll();
	}
	
	@ModelAttribute("fazendas")
	public List<Fazenda> getFazendas(){
		return fazendaRepository.findAll();
	}
	
	@ModelAttribute("status")
	public List<StatusAnimal> getStatus(){
		return utilService.getStatus();
	}
	
	@ModelAttribute("sexos")
	public List<Sexo> getSexos(){
		return utilService.getSexos();
	}
}
