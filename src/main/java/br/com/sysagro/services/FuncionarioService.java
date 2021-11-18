package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.models.Funcionario;
import br.com.sysagro.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;
	
	public List<Funcionario> getFuncionarios(String nome) {
		return !nome.isEmpty() ? repository.findByNomeContainingOrderById("%"+nome.toUpperCase()+"%") : 
								repository.findAll();
		
	}
	

}
