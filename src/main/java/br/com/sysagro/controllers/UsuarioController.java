package br.com.sysagro.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sysagro.models.Perfil;
import br.com.sysagro.models.Usuario;
import br.com.sysagro.repositories.PerfilRepository;
import br.com.sysagro.repositories.UsuarioRepository;
import br.com.sysagro.services.UsuarioService;
import br.com.sysagro.util.Constantes;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private UsuarioService service;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping
	public ModelAndView findAll(@Nullable Optional<String> txtPesquisa) {
		ModelAndView mv = new ModelAndView("usuarios/list");
		mv.addObject("usuarios", service.findByNome(txtPesquisa.orElse("")));			
		return mv;
	}
	
	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("usuarios/form");
		mv.addObject("usuario", usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO)));
		return mv;
	}
	
	@GetMapping("form")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("usuarios/form");
		mv.addObject("usuario", new Usuario());
		return mv;
	}

	@ModelAttribute("perfis")
	public List<Perfil> getPerfis(){
		return perfilRepository.findAll(Sort.by("descricao").ascending()); 
	}
	
	@GetMapping("atualizarStatus/{id}")
	public ModelAndView atualizaStatus(@PathVariable("id") Long id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("redirect:/usuarios");
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		boolean ativo = usuario.isAtivo();
		usuario.setAtivo(ativo ? false : true);
		usuarioRepository.save(usuario);
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return mv;
	}
	
	@PostMapping
	public ModelAndView post(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return new ModelAndView("/usuarios/form"); 
		}
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
		ModelAndView mv = new ModelAndView("redirect:/usuarios");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_GRAVADO_SUCESSO);
		return mv;
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, RedirectAttributes attr) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		usuarioRepository.delete(usuario);
		ModelAndView mv = new ModelAndView("redirect:/usuarios");
		attr.addFlashAttribute("mensagem", Constantes.REGISTRO_EXCLUIDO_SUCESSO);
		return mv;
	}
		
}
