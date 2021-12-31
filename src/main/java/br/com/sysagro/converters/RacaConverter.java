package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.Raca;
import br.com.sysagro.repositories.RacaRepository;

@Component
public class RacaConverter implements Converter<String, Raca> {

	@Autowired
	private RacaRepository dao;
	
	@Override
	public Raca convert(String value) {
		if (value.isEmpty()) return null;
		return dao.findById(Long.parseLong(value)).get();
	}

}
