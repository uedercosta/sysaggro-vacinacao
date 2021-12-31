package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sysagro.models.Transferencia;
import br.com.sysagro.repositories.TransferenciaRepository;

@Service
public class TransferenciaService {
	
	@Autowired
	private TransferenciaRepository repository;

	@Transactional(readOnly = true)
	public List<Transferencia> findAll(){
		return repository.findAll();
	}

	public Transferencia save(Transferencia tr) {
		return repository.save(tr);
	}

}
