package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.TipoVacina;
import br.com.sysagro.repositories.TipoVacinaRepository;

@Component
public class TipoVacinaConverter implements Converter<String, TipoVacina>{

	@Autowired
	private TipoVacinaRepository dao;

	@Override
	public TipoVacina convert(String value) {
		if (value.isEmpty()) return null;
		long id = Long.parseLong(value);
		return dao.findById(id).get();
	}

}
