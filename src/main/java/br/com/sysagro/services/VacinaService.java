package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.models.Vacina;
import br.com.sysagro.repositories.VacinaRepository;

@Service
public class VacinaService {
	
	@Autowired
	private VacinaRepository repository;
	
	public List<Vacina> getVacinas(String descricao){
		if (descricao.isEmpty()) {
			return repository.findAll();
		} else {
			return repository.findByDescricaoContaining("%" + descricao.toUpperCase() + "%");
		}
	}

	public List<Vacina> getVacinas() {
		return repository.findAll();
	}

}
