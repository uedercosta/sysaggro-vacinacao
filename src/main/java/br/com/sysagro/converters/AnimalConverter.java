package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.Animal;
import br.com.sysagro.repositories.AnimalRepository;

@Component
public class AnimalConverter implements Converter<String, Animal>{

	@Autowired
	private AnimalRepository dao;

	@Override
	public Animal convert(String value) {
		if (value.isEmpty()) return null;
		return dao.findById(Long.parseLong(value)).get();
	}
	

}
