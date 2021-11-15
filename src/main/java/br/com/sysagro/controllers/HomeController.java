package br.com.sysagro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = {"/"})
	public String layout(Model model) {
		return "fragments/layout";
	}
	
	@GetMapping(value = {"/home"})
	public String home(Model model) {
		return "home";
	}
}
