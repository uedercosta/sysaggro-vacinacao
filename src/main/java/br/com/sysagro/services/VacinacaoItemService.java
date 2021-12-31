package br.com.sysagro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sysagro.models.VacinacaoItem;
import br.com.sysagro.repositories.VacinacaoItemRepository;
import br.com.sysagro.util.Constantes;

@Service
public class VacinacaoItemService {
	
	@Autowired
	private VacinacaoItemRepository itemRepository;

	@Transactional(readOnly = true)
	public List<VacinacaoItem> getAnimaisVacinados(Long idVacinacao){
		return itemRepository.findByVacinacao(idVacinacao);
	}

	public VacinacaoItem save(VacinacaoItem item) {
		return itemRepository.save(item);
	}

	public void deleteId(Long animalId) {
		VacinacaoItem vacinacaoItem = itemRepository.findById(animalId).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
		itemRepository.delete(vacinacaoItem);
	}
	
	@Transactional(readOnly = true)
	public VacinacaoItem findById(Long id) {
		return itemRepository.findById(id).orElseThrow(()-> new RuntimeException(Constantes.REGISTRO_NAO_ENCONTRADO));
	}
	
	
}
