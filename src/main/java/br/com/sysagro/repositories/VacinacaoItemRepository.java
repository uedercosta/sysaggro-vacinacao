package br.com.sysagro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sysagro.models.VacinacaoItem;

public interface VacinacaoItemRepository extends JpaRepository<VacinacaoItem, Long> {
	
	@Query("select vi from VacinacaoItem vi where vi.vacinacao.id = :id")
	List<VacinacaoItem> findByVacinacao(@Param("id") Long id);
	

}
