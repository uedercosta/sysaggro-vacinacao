package br.com.sysagro.util;

import org.springframework.stereotype.Component;

@Component
public class Util {
	
	public String preencheZeros(String value) {
		return String.format("%06d", value);
	}

}
