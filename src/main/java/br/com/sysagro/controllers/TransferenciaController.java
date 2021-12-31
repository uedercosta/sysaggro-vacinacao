package br.com.sysagro.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysagro.models.Fazenda;
import br.com.sysagro.models.Transferencia;
import br.com.sysagro.repositories.FazendaRepository;
import br.com.sysagro.repositories.TransferenciaRepository;
import br.com.sysagro.services.TransferenciaService;
import br.com.sysagro.util.Constantes;

@Controller
@RequestMapping("transferencias")
public class TransferenciaController {
	
	@Autowired
	private TransferenciaRepository repository;
	@Autowired
	private TransferenciaService service;
	@Autowired
	private FazendaRepository fazendaRepository;
	
	@GetMapping
	public ModelAndView findAll() {
		ModelAndView view = new ModelAndView("/movimentacoes/list");
		view.addObject("transferencias", service.findAll());
		view.addObject("transferencia", new Transferencia());
		return view;
	}
	
	@ModelAttribute("fazendas")
	public List<Fazenda> getFazendas(){
		return fazendaRepository.findAll(); 
	}
	
	@GetMapping("form")
	public ModelAndView form(){
		ModelAndView view = new ModelAndView("/movimentacoes/form");
		view.addObject("transferencia", new Transferencia());
		return view;
	}
	
	@PostMapping
	public ModelAndView post(@ModelAttribute("transferencia") @Valid Transferencia tr, BindingResult result, RedirectAttributes attr){
		if (result.hasErrors()) {
			return new ModelAndView("/movimentacoes/form");
		}
		if (tr.getOrigem().equals(tr.getDestino())) {
			ModelAndView view = new ModelAndView("/movimentacoes/form");
			view.addObject("mensagem", "As fazendas de ORIGEM e de DESTINO, não podem ser iguais");
			return view;
		}
		ModelAndView view = new ModelAndView("redirect:/transferencias");
		tr.gerarNumero(repository);
		service.save(tr);
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view;
	}

}
