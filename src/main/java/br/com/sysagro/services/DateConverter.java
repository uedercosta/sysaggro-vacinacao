package br.com.sysagro.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter implements Converter<String, LocalDate>{

	@Override
	public LocalDate convert(String value) {
		if (value.isEmpty()) return null;
		
		DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataConvertida = LocalDate.parse(value, formater);
		return dataConvertida ;
	}

}
