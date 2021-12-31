package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.Perfil;
import br.com.sysagro.repositories.PerfilRepository;

@Component
public class PerfilConverter implements Converter<String, Perfil>{

	@Autowired
	private PerfilRepository repository;
	
	@Override
	public Perfil convert(String value) {
		if(value.isEmpty())	return null;
		return repository.findById(Long.parseLong(value)).get();
	}

}
