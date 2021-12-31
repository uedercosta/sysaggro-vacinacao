package br.com.sysagro.controllers;

import java.io.IOException;
import java.sql.SQLException;
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

import br.com.sysagro.models.Fazenda;
import br.com.sysagro.models.Proprietario;
import br.com.sysagro.models.enums.Estado;
import br.com.sysagro.repositories.FazendaRepository;
import br.com.sysagro.repositories.ProprietarioRepository;
import br.com.sysagro.services.FazendaService;
import br.com.sysagro.services.UtilService;
import br.com.sysagro.util.Constantes;
import br.com.sysagro.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;

@RequestMapping("/fazendas")
@Controller
public class FazendaController {
	
	@Autowired
	private FazendaRepository fazendaRepository;
	@Autowired
	private FazendaService service;
	@Autowired
	private UtilService utilService;
	@Autowired
	private ProprietarioRepository propRepository;
	@Autowired
	private ReportsUtil reportsUtil;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/fazendas/list");
		view.addObject("fazendas", service.getFazendas(txtPesquisa.orElse("")));
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/fazendas/form");
		view.addObject("fazenda", new Fazenda());
		return view;
	}
	
	@ModelAttribute("estados")
	public List<Estado> getEstados() {
		return utilService.getEstados();
	}
		

	@ModelAttribute("proprietarios")
	public List<Proprietario> getProprietarios() {
		return propRepository.findAll();
	}

	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/fazendas/form");
		Fazenda fazenda = fazendaRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("fazenda", fazenda);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("fazenda") @Valid Fazenda entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/fazendas/form"); 
		}
		fazendaRepository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/fazendas");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		Fazenda fazenda = fazendaRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		fazendaRepository.delete(fazenda);
		ModelAndView view = new ModelAndView("redirect:/fazendas");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
	
	@GetMapping("/print")
	public void imprimir(@Nullable Optional<String> txtPesquisa, HttpServletResponse response) throws JRException, SQLException, IOException {
		List<Fazenda> fazendas = service.getFazendas(txtPesquisa.orElse(""));
		reportsUtil.imprimir(response, fazendas, "reportFazendas");
	}
}
