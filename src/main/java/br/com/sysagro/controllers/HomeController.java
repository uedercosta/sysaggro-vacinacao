package br.com.sysagro.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HomeController implements ErrorController{
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("erroLogin","Dados de acesso inválido ou o seu usuário está inativo");
		return "login";
	}
	
	@GetMapping({"/home","/"})
	public String home() {
		return "home";
	}
	
	@GetMapping("/acesso-negado")
	public String acessoNegado(Model model, HttpServletResponse response) {
		model.addAttribute("errorCode", response.getStatus());
		model.addAttribute("errorMessage", "Acesso negado a função selecionada...");
		return "error";
	}

	@RequestMapping("/error")
	public String handleError(ModelMap model, HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	  
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	    		model.addAttribute("errorCode", statusCode);
	    		model.addAttribute("errorMessage", "Página não encontrada...");
	            return "error";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	    		model.addAttribute("errorCode", statusCode);
	    		model.addAttribute("errorMessage", "Erro interno do sistema...");
	            return "error";
	        }
	    }
	    model.addAttribute("errorCode", status);
		model.addAttribute("errorMessage", "Erro não identificado...");
	    return "error";
	}
	
	
}
