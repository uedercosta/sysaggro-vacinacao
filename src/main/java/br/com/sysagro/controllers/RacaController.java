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

import br.com.sysagro.models.Raca;
import br.com.sysagro.repositories.RacaRepository;
import br.com.sysagro.util.Constantes;
import br.com.sysagro.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;

@RequestMapping("/racas")
@Controller
public class RacaController {
	
	@Autowired
	private RacaRepository repository;
	@Autowired
	private ReportsUtil reportsUtil;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {	
		ModelAndView view = new ModelAndView("/racas/list");
		if (txtPesquisa.isPresent()) {
			view.addObject("racas", repository.findByDescricaoContaining(txtPesquisa.get().toUpperCase()));
		} else {
			view.addObject("racas", repository.findAll());
		}
		return view;
	}

	@GetMapping("/form")
	public ModelAndView form() {
		ModelAndView view = new ModelAndView("/racas/form");
		view.addObject("raca", new Raca());
		return view;
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView view = new ModelAndView("/racas/form");
		Raca raca =  repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		view.addObject("raca", raca);
		return view;
	}
	
	@PostMapping
	public ModelAndView save(@ModelAttribute("raca") @Valid Raca entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/racas/form"); 
		}
		repository.save(entity);
		ModelAndView view = new ModelAndView("redirect:/racas");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return view; 
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView deleteId(@PathVariable("id") Long id, RedirectAttributes attr) {
		Raca raca = repository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		repository.delete(raca);
		ModelAndView view = new ModelAndView("redirect:/racas");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return view; 
	}
	
	@GetMapping("/print")
	public void imprimir(@Nullable Optional<String> txtPesquisa, HttpServletResponse response) throws JRException, SQLException, IOException {
		List<Raca> racas = new ArrayList<Raca>();
		if (txtPesquisa.isPresent()) {
			racas =  repository.findByDescricaoContaining(txtPesquisa.get().toUpperCase());
		} else {
			racas = repository.findAll();
		}
		reportsUtil.imprimir(response, racas, "reportRacas");
	}
}
