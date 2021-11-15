package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.Fazenda;
import br.com.sysagro.repositories.FazendaRepository;

@Component
public class FazendaConverter implements Converter<String, Fazenda>{

	@Autowired
	private FazendaRepository repository;
	
	@Override
	public Fazenda convert(String value) {
		if (value.isEmpty()) return null;
		long id = Long.parseLong(value);
		return repository.findById(id).get();
	}

}
