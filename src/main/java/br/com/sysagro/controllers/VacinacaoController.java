package br.com.sysagro.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.sysagro.models.Vacina;
import br.com.sysagro.models.Vacinacao;
import br.com.sysagro.models.VacinacaoItem;
import br.com.sysagro.models.enums.StatusVacinacao;
import br.com.sysagro.repositories.FazendaRepository;
import br.com.sysagro.repositories.VacinacaoItemRepository;
import br.com.sysagro.repositories.VacinacaoRepository;
import br.com.sysagro.services.VacinaService;
import br.com.sysagro.services.VacinacaoService;
import br.com.sysagro.util.Constantes;
import br.com.sysagro.util.ReportsUtil;
import net.sf.jasperreports.engine.JRException;

@RequestMapping("vacinacoes")
@Controller
public class VacinacaoController {

	@Autowired
	private VacinacaoRepository vacinacaoRepository;
	@Autowired
	private VacinacaoService vacinacaoService;
	@Autowired
	private VacinaService vacinaService;
	@Autowired
	private FazendaRepository fazendaRepository;
	@Autowired
	private VacinacaoItemRepository itemRepository;
	@Autowired
	private ReportsUtil report;
	

	@GetMapping
	public ModelAndView list(Optional<String> fazenda, Optional<Integer> ano) {
		if (!fazenda.isPresent() || fazenda.get().equals("Todas")) {
			fazenda = Optional.of("");
		}
		ModelAndView mv = new ModelAndView("/vacinacoes/list");
		mv.addObject("vacinacoes",vacinacaoService.getVacinacoes(fazenda.orElse(""), ano.orElse(0)));
		return mv;
	}

	public ModelAndView mudarStatus(Long id, StatusVacinacao status, RedirectAttributes attr) {
		Vacinacao vacinacao = vacinacaoService.findVacinacaoId(id);
		ModelAndView mv = new ModelAndView("redirect:/vacinacoes");
		vacinacao.setStatus(status);
		if (status == StatusVacinacao.CANCELADO) {
			vacinacao.setDataCancelamento(LocalDate.now());
		}
		vacinacaoRepository.save(vacinacao);
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return mv;
	}

	private ModelAndView addVacinacao(Vacinacao vacinacao) {
		ModelAndView mv = new ModelAndView("/vacinacoes/form");
		mv.addObject("vacinacao", vacinacao);
		return mv;
	}

	@GetMapping("form")
	public ModelAndView form() {
		Vacinacao vacinacao = new Vacinacao();
		return addVacinacao(vacinacao);
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes attr) {
		Vacinacao vacinacao = vacinacaoService.findVacinacaoId(id);
		ModelAndView mv = null;
		if (!vacinacao.permiteAlteracao()) {
			mv = new ModelAndView("redirect:/vacinacoes");
			attr.addFlashAttribute("mensagem", Constantes.VACINACAO_BLOQUEADA);
		} else {
			mv = addVacinacao(vacinacao);
		}
		return mv;
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("redirect:/vacinacoes");
		Vacinacao vacinacao = vacinacaoService.findVacinacaoId(id);
		if (!vacinacao.permiteAlteracao()) {
			attr.addFlashAttribute("mensagem", Constantes.VACINACAO_BLOQUEADA);
		} else {
			vacinacaoService.delete(id);
		}
		return mv;
	}

	@ModelAttribute("vacinas")
	public List<Vacina> getVacinas() {
		return vacinaService.getVacinas();
	}

	@ModelAttribute("fazendas")
	public List<Fazenda> getFazendas() {
		return fazendaRepository.findAll();
	}

	@PostMapping
	public ModelAndView post(@Valid Vacinacao entity, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return addVacinacao(entity);
		}
		Vacinacao vacinacaoId = vacinacaoRepository.save(entity);
		ModelAndView mv = new ModelAndView("redirect:/vacinacoes/" + vacinacaoId.getId() + "/addAnimal");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return mv;
	}

	@GetMapping("view/{id}")
	public ModelAndView view(@PathVariable("id") Long id) {
		Vacinacao vacinacao = vacinacaoService.findVacinacaoId(id);
		ModelAndView mv = new ModelAndView("/vacinacoes/view");
		mv.addObject("vacinacao", vacinacao);
		mv.addObject("items", itemRepository.findByVacinacao(vacinacao.getId()));
		return mv;
	}

	@GetMapping("finalizar/{id}")
	public ModelAndView finalizar(@PathVariable("id") Long id, RedirectAttributes attr) {
		Vacinacao vacinacao = vacinacaoService.findVacinacaoId(id);
		
		if (!vacinacao.permiteAlteracao()) {
			ModelAndView mv = new ModelAndView("redirect:/vacinacoes");
			attr.addFlashAttribute("mensagem", Constantes.VACINACAO_BLOQUEADA);
			return mv;
		} else {
			if (itemRepository.findByVacinacao(id).size() == 0) {
				ModelAndView mv = new ModelAndView("redirect:/vacinacoes");
				attr.addFlashAttribute("mensagem", "Não é possível finalizar sem que possua animais informados");
				return mv;				
			} else {				
				return mudarStatus(id, StatusVacinacao.FINALIZADO, attr);
			}
		}
	}

	@GetMapping("cancelar/{id}")
	public ModelAndView cancelar(@PathVariable("id") Long id, RedirectAttributes attr) {
		Vacinacao vacinacao = vacinacaoService.findVacinacaoId(id);
		if (!vacinacao.permiteAlteracao()) {
			ModelAndView mv = new ModelAndView("redirect:/vacinacoes");
			attr.addFlashAttribute("mensagem", Constantes.VACINACAO_BLOQUEADA);
			return mv;
		} else {
			return mudarStatus(id, StatusVacinacao.CANCELADO, attr);
		}
	}
	
	@GetMapping("print/{id}")
	public void imprimir(@PathVariable("id") Long id, HttpServletResponse response) throws JRException, SQLException, IOException {
		List<VacinacaoItem> items = itemRepository.findByVacinacao(id);
		report.imprimir(response, items, "reportRelatorioVacinacao");
	}

}
