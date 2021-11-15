package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.models.Proprietario;
import br.com.sysagro.repositories.ProprietarioRepository;

@Service
public class ProprietarioService {
	
	@Autowired
	private ProprietarioRepository repository;

	public List<Proprietario> getProprietarios(String descricao) {
		if (descricao.isEmpty()) {
			return repository.findAll();
		} else {
			return repository.findByNomeContainingOrderById(descricao.toUpperCase());
		}
	}
	
	

}
