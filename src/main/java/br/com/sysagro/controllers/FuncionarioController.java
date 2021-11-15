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

import br.com.sysagro.models.Fazenda;
import br.com.sysagro.models.Funcionario;
import br.com.sysagro.models.enums.Estado;
import br.com.sysagro.repositories.FazendaRepository;
import br.com.sysagro.repositories.FuncionarioRepository;
import br.com.sysagro.services.FuncionarioService;
import br.com.sysagro.services.UtilService;
import br.com.sysagro.util.Constantes;

@RequestMapping("/funcionarios")
@Controller
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private FuncionarioService service;
	@Autowired
	private UtilService utilService;
	@Autowired
	private FazendaRepository fazendaRepository;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/funcionarios/list");
		view.addObject("funcionarios", service.getFuncionarios(txtPesquisa.orElse("")));
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/funcionarios/form");
		view.addObject("funcionario", new Funcionario());
		return view;
	}
	
	@ModelAttribute("estados")
	public List<Estado> getEstados() {
		return utilService.getEstados();
	}
	
	@ModelAttribute("fazendas")
	public List<Fazenda> getFazendas(){
		return fazendaRepository.findAll();
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/funcionarios/form");
		Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("funcionario", funcionario);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("funcionario") @Valid Funcionario entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/funcionarios/form"); 
		}
		funcionarioRepository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/funcionarios");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		funcionarioRepository.delete(funcionario);
		ModelAndView view = new ModelAndView("redirect:/funcionarios");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
}
