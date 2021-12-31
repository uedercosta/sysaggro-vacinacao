package br.com.sysagro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sysagro.models.Vacinacao;
import br.com.sysagro.models.enums.StatusVacinacao;
import br.com.sysagro.repositories.VacinacaoRepository;
import br.com.sysagro.util.Constantes;

@Service
public class VacinacaoService {
	
	@Autowired
	private VacinacaoRepository vacinacaoRepository;

	public List<Vacinacao> getVacinacoes(String fazenda, Integer ano) {
		List<Vacinacao> list = new ArrayList<Vacinacao>();
		if ((fazenda.equals("")) && (ano == 0)) {
			list = vacinacaoRepository.findAll();
		} else if (fazenda.equals("") && (ano != 0)) {
			list = vacinacaoRepository.findAll(ano); 
		} else if (!fazenda.equals("") && (ano != 0)) {
			list = vacinacaoRepository.findAll(fazenda, ano);
		} else {
			list = vacinacaoRepository.findByFazendaIdOrderById(Long.parseLong(fazenda));
		}
		return list;
	}

	public List<Vacinacao> getVacinacoes() {
		return vacinacaoRepository.findAll();
	}

	public Vacinacao findVacinacaoId(Long id) {
		return vacinacaoRepository.findById(id).orElseThrow(()-> new RuntimeException("Registro não encontrado..."));
	}

	public void delete(Long id) {
		Vacinacao vacinacao = vacinacaoRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		if (vacinacao.getStatus() == StatusVacinacao.EM_ANDAMENTO) {
			vacinacaoRepository.delete(vacinacao);
		}
		
		if (vacinacao.getStatus() == StatusVacinacao.CANCELADO) {
			throw new RuntimeException(Constantes.VACINACAO_CANCELADA_BLOQUEADA_EXCLUSAO);
		}
		if (vacinacao.getStatus() == StatusVacinacao.FINALIZADO) {
			throw new RuntimeException(Constantes.VACINACAO_CONFIRMADA_BLOQUEADA_EXCLUSAO);
		}		
	}
	
	
	
}
