package br.com.sysagro.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.sysagro.models.Funcionario;
import br.com.sysagro.repositories.FuncionarioRepository;

@Component
public class FuncionarioConverter implements Converter<String, Funcionario>{

	@Autowired
	private FuncionarioRepository dao;
	
	@Override
	public Funcionario convert(String value) {
		if (value.isEmpty()) return null;
		return dao.findById(Long.parseLong(value)).get();
	}

}
