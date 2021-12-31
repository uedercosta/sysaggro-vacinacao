package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.Proprietario;
import br.com.sysagro.repositories.ProprietarioRepository;

@Component
public class ProprietarioConverter implements Converter<String, Proprietario>{

	@Autowired
	private ProprietarioRepository dao;
	
	@Override
	public Proprietario convert(String value) {
		if (value.isEmpty()) return null;
		return dao.findById(Long.parseLong(value)).get();
	}

}
