package br.com.sysagro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.dto.AnimalDTO;
import br.com.sysagro.models.Animal;
import br.com.sysagro.models.enums.Sexo;
import br.com.sysagro.repositories.AnimalRepository;

@Service
public class AnimalService {
	
	@Autowired
	private AnimalRepository repository;
	
	public List<Animal> getAnimais(String nome){
		return nome.isEmpty() ? repository.findAll()
							  : repository.findByNome("%"+nome.toUpperCase()+"%");
	}
	
	public List<Animal> getAllMachos(){
		return repository.findAllBySexo(Sexo.M);
	}

	public List<Animal> getAllFemeas(){
		return repository.findAllBySexo(Sexo.F);
	}
	
	public List<AnimalDTO> getAnimaisDTO(String nome){
		if (nome.isEmpty()) {
			return repository.findAll().stream().map( a -> new AnimalDTO().toDTO(a)).collect(Collectors.toList());
		} else {
			return repository.findByNome("%"+nome.toUpperCase()+"%")
						.stream().map(a -> new AnimalDTO().toDTO(a))
						.collect(Collectors.toList());
		}
	}
	
	

}
