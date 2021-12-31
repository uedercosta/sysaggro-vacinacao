package br.com.sysagro.controllers;

import java.util.List;

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

import br.com.sysagro.models.Animal;
import br.com.sysagro.models.Vacinacao;
import br.com.sysagro.models.VacinacaoItem;
import br.com.sysagro.repositories.AnimalRepository;
import br.com.sysagro.repositories.VacinacaoRepository;
import br.com.sysagro.services.VacinacaoItemService;
import br.com.sysagro.util.Constantes;

@Controller
@RequestMapping
public class VacinacaoItemController {

	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private VacinacaoRepository vacinacaoRepository;
	@Autowired
	private VacinacaoItemService vacinacaoItemService;

	public List<Animal> getAnimais(Long id) {
		return animalRepository.findByFazenda(id);
	}

	@GetMapping("/vacinacoes/{id}/addAnimal")
	public ModelAndView addAnimal(@PathVariable("id") Long id) {
		Vacinacao vacinacao = vacinacaoRepository.findById(id).orElseThrow(() -> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		ModelAndView mv = new ModelAndView("/vacinacoes/addAnimal");
		mv.addObject("vacinacao", vacinacao);
		mv.addObject("items", vacinacaoItemService.getAnimaisVacinados(vacinacao.getId()));
		mv.addObject("animais", getAnimais(vacinacao.getFazenda().getId()));
		mv.addObject("item", new VacinacaoItem());
		return mv;
	}

	@PostMapping("/vacinacoes/{id}/addAnimal")
	public ModelAndView post(@ModelAttribute("item") @Valid VacinacaoItem item, BindingResult result, RedirectAttributes attr, @PathVariable("id")  Long idVacinacao ) {
		Vacinacao vacinacao = vacinacaoRepository.findById(idVacinacao).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		item.setVacinacao(vacinacao);
		if (item.animalJaVacinado(vacinacaoItemService)) {
			attr.addFlashAttribute("mensagem", "Animal já vacinado...");
			ModelAndView mv = new ModelAndView("/vacinacoes/addAnimal");
			mv.addObject("vacinacao", vacinacao);
			mv.addObject("items", vacinacaoItemService.getAnimaisVacinados(vacinacao.getId()));
			mv.addObject("animais", getAnimais(vacinacao.getFazenda().getId()));
			return mv;
		}
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("/vacinacoes/addAnimal");
			return mv;
		}
		ModelAndView mv = new ModelAndView("redirect:/vacinacoes/"+idVacinacao+"/addAnimal");
		vacinacaoItemService.save(item);
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return mv;
	}
	
	@GetMapping("/vacinacoes/deleteAnimal/{animal}")
	public ModelAndView deleteAnimal(@PathVariable("animal") Long animalId) {
		VacinacaoItem item = vacinacaoItemService.findById(animalId);
		ModelAndView mv = new ModelAndView("redirect:/vacinacoes/" + item.getVacinacao().getId() + "/addAnimal");
		vacinacaoItemService.deleteId(animalId);
		return mv;
	}

}
