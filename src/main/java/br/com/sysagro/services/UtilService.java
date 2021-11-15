package br.com.sysagro.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sysagro.models.enums.Estado;
import br.com.sysagro.models.enums.Sexo;

@Service
public class UtilService {

	public List<Estado> getEstados(){
		return Arrays.asList(Estado.values());
	}
	
	
	public List<Sexo> getSexos(){
		return Arrays.asList(Sexo.values());
	}
}
