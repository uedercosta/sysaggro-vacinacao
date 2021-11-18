package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.models.Fazenda;
import br.com.sysagro.repositories.FazendaRepository;

@Service
public class FazendaService {
	
	@Autowired
	private FazendaRepository repository;
	
	public List<Fazenda> getFazendas(String descricao){
		return descricao.isEmpty() ? repository.findAll()
								   : repository.findNomeContaining("%" + descricao.toUpperCase() + "%");
	}

}
