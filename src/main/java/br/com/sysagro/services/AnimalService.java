package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.models.Animal;
import br.com.sysagro.repositories.AnimalRepository;

@Service
public class AnimalService {
	
	@Autowired
	private AnimalRepository repository;
	
	public List<Animal> getAnimais(String nome){
		return nome.isEmpty() ? repository.findAll()
							  : repository.findByNome("%"+nome.toUpperCase()+"%");
	}

}
